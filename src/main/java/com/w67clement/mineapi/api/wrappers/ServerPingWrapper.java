package com.w67clement.mineapi.api.wrappers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.system.MC_GameProfile;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

/**
 * Use and change the ServerPing!
 *
 * @author w67clement
 */
public interface ServerPingWrapper
{

    /**
     * Gets the Motd in the ServerPing!
     *
     * @return Motd
     */
    String getMotd();

    /**
     * Change the motd.
     *
     * @param obj The ChatComponent object value
     */
    void setMotd(Object obj);

    /**
     * Gets the Motd in the ServerPing!
     *
     * @return An object, who represent the ChatComponent motd
     */
    Object getChatComponentMotd();

    /**
     * Gets the version's name in the ServerPingData!
     *
     * @return The version's name
     */
    String getVersionName();

    /**
     * Change the version name!
     *
     * @param version The Version name value
     */
    void setVersionName(String version);

    /**
     * Gets the protocol in the ServerPingData!
     *
     * @return The protocol version!
     */
    int getProtocolVersion();

    /**
     * Change the protocol version!
     *
     * @param protocol Version of Protocol
     */
    void setProtocolVersion(int protocol);

    /**
     * Gets the number of online players!
     *
     * @return Number of Online Players present in the ServerPing.
     */
    int getOnlinesPlayers();

    /**
     * Change the number of online players!
     *
     * @param onlines Number of Online Players
     */
    void setOnlinesPlayers(int onlines);

    /**
     * Gets the number of maximum players!
     *
     * @return Number of maximum players present in ServerPing
     */
    int getMaximumPlayers();

    /**
     * Change the number of maximum players!
     *
     * @param max Number
     */
    void setMaximumPlayers(int max);

    /**
     * Gets the player list!
     *
     * @return The list in ServerPing
     */
    List<OfflinePlayer> getPlayerList();

    /**
     * Change the player list!
     *
     * @param players The list of Offline players!
     */
    void setPlayerList(List<OfflinePlayer> players);

    /**
     * Gets the player list!
     *
     * @return The list in ServerPing
     */
    List<MC_GameProfile> getProfilesList();

    /**
     * Change the player list!
     *
     * @param players The list of players' name!
     */
    void setPlayerListWithName(List<String> players);

    /**
     * Change the player list!
     *
     * @param players The list of GameProfiles!
     */
    void setPlayerListWithGameProfile(List<MC_GameProfile> players);

    /**
     * Gets the favicon encoded in Base64.
     *
     * @return Favicon.
     */
    String getFavicon();

    /**
     * Sets the favicon, it must be 64*64 pixels and encoded in Base64.
     *
     * @param favicon Favicon.
     */
    void setFavicon(String favicon);

    /**
     * Gets the ServerPing object.
     *
     * @return ServerPing Nms' object.
     */
    Object toServerPing();

    /**
     * Gets the ServerPing data in Json.
     *
     * @return ServerPing in Json.
     */
    String toJson();

    class Serializer implements JsonSerializer<ServerPingWrapper>, JsonDeserializer<ServerPingWrapper>
    {
        private static Serializer serializer = new Serializer();

        public static ServerPingWrapper jsonToServerPing(String json)
        {
            return serializer.deserialize(new JsonParser().parse(json), null, null);
        }

        @Override
        public ServerPingWrapper deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
        {
            ServerPingWrapper obj = MineAPI.getNmsManager().getServerPingWrapper();
            if (jsonElement instanceof JsonObject)
            {
                JsonObject json = (JsonObject) jsonElement;
                if (json.get("version") instanceof JsonObject)
                {
                    JsonObject version = json.getAsJsonObject("version");
                    obj.setVersionName(version.get("name").getAsString());
                    obj.setProtocolVersion(version.get("protocol").getAsInt());
                }
                if (json.get("description") instanceof JsonObject)
                {
                    JsonObject description = json.getAsJsonObject("description");
                    obj.setMotd(description.get("text").getAsString());
                }
                if (json.get("players") instanceof JsonObject)
                {
                    JsonObject playerData = json.getAsJsonObject("players");
                    obj.setOnlinesPlayers(playerData.get("online").getAsInt());
                    obj.setMaximumPlayers(playerData.get("max").getAsInt());
                    if (json.get("sample") instanceof JsonArray)
                    {
                        JsonArray sample = json.getAsJsonArray("sample");
                        List<MC_GameProfile> players = new ArrayList<>();
                        sample.forEach(element -> {
                            if (element instanceof JsonObject)
                                players.add(MC_GameProfile.fromJson(element));
                        });
                        obj.setPlayerListWithGameProfile(players);
                    }
                }
                if (json.has("favicon"))
                    obj.setFavicon(json.get("favicon").getAsString());
            }
            return obj;
        }

        @Override
        public JsonElement serialize(ServerPingWrapper obj, Type type, JsonSerializationContext context)
        {
            JsonObject json = new JsonObject();

            // Version data
            JsonObject version = new JsonObject();
            version.addProperty("protocol", obj.getProtocolVersion());
            version.addProperty("name", obj.getVersionName() + ChatColor.RESET);
            json.add("version", version);

            // Motd
            JsonObject motd = new JsonObject();
            motd.addProperty("text", obj.getMotd() + ChatColor.RESET);
            json.add("description", motd);

            // Favicon
            if (obj.getFavicon() != null)
                json.addProperty("favicon", obj.getFavicon());

            // Players data
            JsonObject players = new JsonObject();
            players.addProperty("online", obj.getOnlinesPlayers());
            players.addProperty("max", obj.getMaximumPlayers());

            JsonArray sample = new JsonArray();
            obj.getPlayerList().forEach(player -> {
                JsonObject player_json = new JsonObject();
                player_json.addProperty("name", player.getName() + ChatColor.RESET);
                player_json.addProperty("id", player.getUniqueId().toString());
                sample.add(player_json);
            });

            players.add("sample", sample);

            json.add("players", players);

            return json;
        }
    }
}
