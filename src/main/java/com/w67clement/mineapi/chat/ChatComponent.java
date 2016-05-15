package com.w67clement.mineapi.chat;

import com.google.gson.*;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.chat.ChatComponentClickEvent.ClickEventAction;
import com.w67clement.mineapi.chat.ChatComponentHoverEvent.HoverEventAction;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by w67clement on 06/04/2016.
 * <p>
 * Class of project: MineAPI
 */
public class ChatComponent
{
    private List<ChatComponentPart> parts = new ArrayList<>();

    public ChatComponent(String text)
    {
        this.parts.add(new ChatComponentPart(text));
    }

    /**
     * Gets all parts of the ChatComponent.
     *
     * @return All parts of the ChatComponent.
     */
    public List<ChatComponentPart> getChatComponentParts()
    {
        return parts;
    }

    /**
     * Gets the latest part of ChatComponent.
     *
     * @return Latest part.
     */
    public ChatComponentPart lastPart()
    {
        return parts.get(parts.size() - 1);
    }

    /**
     * Sets the color for the latest part of ChatComponent.
     *
     * @param color Color.
     *
     * @return Actual instance.
     */
    public ChatComponent color(ChatColor color)
    {
        if (!color.isColor())
        {
            throw new IllegalArgumentException(color.name() + " is not a color");
        }
        lastPart().setColor(color);
        return this;
    }

    /**
     * Sets the styles for the latest part of ChatComponent.
     *
     * @param styles Styles.
     *
     * @return Actual instance.
     */
    public ChatComponent style(ChatColor... styles)
    {
        for (ChatColor style : styles)
        {
            if (!style.isFormat())
            {
                throw new IllegalArgumentException(style.name() + " is not a style");
            }
        }
        lastPart().setStyles(styles);
        return this;
    }

    /**
     * Sets the click event for the latest part of ChatComponent.
     *
     * @param clickEvent Click Event data.
     *
     * @return Actual instance.
     */
    public ChatComponent click(ChatComponentClickEvent clickEvent)
    {
        Validate.notNull(clickEvent, "ClickEvent cannot be null.");
        lastPart().setClickEvent(clickEvent);
        return this;
    }

    /**
     * Sets the hover event for the latest part of ChatComponent.
     *
     * @param hoverEvent Hover Event data.
     *
     * @return Actual instance.
     */
    public ChatComponent hover(ChatComponentHoverEvent hoverEvent)
    {
        Validate.notNull(hoverEvent, "HoverEvent cannot be null.");
        lastPart().setHoverEvent(hoverEvent);
        return this;
    }

    /**
     * Add new part in the ChatComponent with text.
     *
     * @param text Text of the new part.
     *
     * @return Actual instance.
     */
    public ChatComponent then(String text)
    {
        this.parts.add(new ChatComponentPart(text));
        return this;
    }

    /**
     * Sends the Message with the PacketPlayOutChat packet to player.
     *
     * @param player Player who received the message.
     */
    public void send(Player player)
    {
        MineAPI.getNmsManager().getPacketChat(this.toJson()).send(player);
    }

    /**
     * Sends the Message with the PacketPlayOutChat packet to all players.
     */
    public void sendAll() {
        MineAPI.getNmsManager().getPacketChat(this.toJson()).sendAll();
    }

    public String toJson()
    {
        return ChatSerializer.serialize(this);
    }

    public String toText()
    {
        StringBuilder builder = new StringBuilder();
        parts.forEach(part -> builder.append(part.getText()));
        return builder.toString();
    }

    public String toTextWithColor()
    {
        StringBuilder builder = new StringBuilder();
        parts.forEach(part -> builder.append(part.getColor().toString()).append(part.getText()));
        return builder.toString();
    }

    public static class ChatSerializer implements JsonSerializer<ChatComponent>, JsonDeserializer<ChatComponent>
    {

        private static Gson gson;

        static
        {
            GsonBuilder var0 = new GsonBuilder();
            var0.registerTypeHierarchyAdapter(ChatComponent.class, new ChatSerializer());
            var0.registerTypeHierarchyAdapter(ChatComponentPart.class, new ChatPartSerializer());
            gson = var0.create();
        }

        private JsonParser parser = new JsonParser();

        public static ChatComponent deserialize(String json)
        {
            return gson.fromJson(json, ChatComponent.class);
        }

        public static String serialize(ChatComponent component)
        {
            return gson.toJson(component);
        }

        public ChatComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            if (json instanceof JsonObject)
            {
                JsonObject obj = (JsonObject) json;
                ChatComponent msg;
                if (obj.has("extra"))
                {
                    JsonArray extra = obj.getAsJsonArray("extra");
                    List<ChatComponentPart> var8 = new ArrayList<>();
                    extra.forEach(part -> var8.add(gson.fromJson(part, ChatComponentPart.class)));
                    ChatComponent var9 = new ChatComponent("");
                    var9.parts = var8;
                    msg = var9;
                }
                else
                {
                    ChatComponent var7 = new ChatComponent("");
                    var7.parts = Collections.singletonList(gson.fromJson(json, ChatComponentPart.class));
                    msg = var7;
                }
                return msg;
            }
            return null;
        }

        public JsonElement serialize(ChatComponent src, Type typeOfSrc, JsonSerializationContext context)
        {
            JsonObject json = new JsonObject();
            if (src.parts.size() == 1)
            {
                json = (JsonObject) parser.parse(gson.toJson(src.lastPart()));
            }
            else
            {
                json.addProperty("text", "");
                JsonArray extra = new JsonArray();
                src.parts.forEach(part -> extra.add(parser.parse(gson.toJson(part))));
                json.add("extra", extra);

            }
            return json;
        }
    }

    private static class ChatPartSerializer implements JsonSerializer<ChatComponentPart>, JsonDeserializer<ChatComponentPart>
    {

        public ChatComponentPart deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            if (json instanceof JsonObject)
            {
                JsonObject obj = (JsonObject) json;
                ChatComponentPart part = new ChatComponentPart(obj.get("text").getAsString());
                // Color
                if (obj.has("color"))
                {
                    String color = obj.get("color").getAsString();
                    part.color = ChatColor.valueOf(color.toUpperCase());
                }
                // Styles
                List<ChatColor> styles = new ArrayList<>();
                obj.entrySet().forEach(entry -> {
                    for (ChatColor values : ChatColor.values())
                    {
                        if (values.isFormat())
                            if (entry.getKey().equals(values.name().toLowerCase()))
                            {
                                styles.add(values);
                            }
                            else if (entry.getKey().equals("underlined"))
                            {
                                styles.add(ChatColor.UNDERLINE);
                            }
                    }
                });
                if (!styles.isEmpty())
                {
                    part.styles = styles.toArray(new ChatColor[styles.size()]);
                }
                // ClickEvent
                if (obj.has("clickEvent"))
                {
                    JsonObject clickEvent = obj.getAsJsonObject("clickEvent");
                    ClickEventAction action = ClickEventAction.getByJsonAction(clickEvent.get("action").getAsString());
                    if (action != null)
                    {
                        part.setClickEvent(new ChatComponentClickEvent(ClickEventAction.getByJsonAction(clickEvent.get("action").getAsString()), clickEvent.get("value").getAsString()));
                    }
                }
                // HoverEvent
                if (obj.has("hoverEvent"))
                {
                    JsonObject hoverEvent = obj.getAsJsonObject("hoverEvent");
                    HoverEventAction action = HoverEventAction.getByJsonAction(hoverEvent.get("action").getAsString());
                    if (action != null)
                    {
                        part.setClickEvent(new ChatComponentClickEvent(ClickEventAction.getByJsonAction(hoverEvent.get("action").getAsString()), hoverEvent.get("value").getAsString()));
                    }
                }
                return part;
            }
            return null;
        }

        public JsonElement serialize(ChatComponentPart src, Type typeOfSrc, JsonSerializationContext context)
        {
            JsonObject json = new JsonObject();
            json.addProperty("text", src.text);
            if (src.color != null)
            {
                json.addProperty("color", src.color.name().toLowerCase());
            }
            if (src.styles != null)
            {
                for (final ChatColor style : src.styles)
                {
                    json.addProperty(style == ChatColor.UNDERLINE ? "underlined" : style.name().toLowerCase(), true);
                }
            }
            if (src.clickEvent != null)
            {
                JsonObject clickEvent = new JsonObject();
                clickEvent.addProperty("action", src.clickEvent.getAction().getJsonAction());
                clickEvent.addProperty("value", src.clickEvent.getValue());

                json.add("clickEvent", clickEvent);
            }
            if (src.hoverEvent != null)
            {
                JsonObject hoverEvent = new JsonObject();
                hoverEvent.addProperty("action", src.hoverEvent.getAction().getJsonAction());
                hoverEvent.addProperty("value", src.hoverEvent.getValue());

                json.add("hoverEvent", hoverEvent);
            }
            return json;
        }
    }

    public static class ChatComponentPart
    {
        private String text;
        private ChatColor color = ChatColor.RESET;
        private ChatColor[] styles = null;
        private ChatComponentHoverEvent hoverEvent;
        private ChatComponentClickEvent clickEvent;

        public ChatComponentPart(String text)
        {
            this.text = text;
        }

        public ChatComponentPart(String text, ChatColor color)
        {
            this.text = text;
            this.color = color;
        }

        public String getText()
        {
            return text;
        }

        public void setText(String text)
        {
            this.text = text;
        }

        public ChatColor getColor()
        {
            return color;
        }

        public void setColor(ChatColor color)
        {
            Validate.notNull(color, "ChatColor cannot be null.");
            this.color = color;
        }

        public ChatColor[] getStyles()
        {
            return styles;
        }

        public void setStyles(ChatColor[] styles)
        {
            this.styles = styles;
        }

        public ChatComponentHoverEvent getHoverEvent()
        {
            return hoverEvent;
        }

        public void setHoverEvent(ChatComponentHoverEvent hoverEvent)
        {
            this.hoverEvent = hoverEvent;
        }

        public ChatComponentClickEvent getClickEvent()
        {
            return clickEvent;
        }

        public void setClickEvent(ChatComponentClickEvent clickEvent)
        {
            this.clickEvent = clickEvent;
        }

        @Override
        public String toString()
        {
            return text;
        }
    }
}
