package com.w67clement.mineapi.api.wrappers;

import com.google.gson.JsonObject;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.message.FancyMessage;
import java.lang.reflect.Method;

public class ChatComponentWrapper
{

    public static Object makeChatComponentByText(String text)
    {
        JsonObject json = new JsonObject();
        json.addProperty("text", text);
        return makeChatComponentByJson(json.toString());
    }

    public static Object makeChatComponentByJson(String json)
    {
        if (MineAPI.isGlowstone())
        {
            return json;
        }
        Method a = ReflectionAPI.getMethod(ReflectionAPI.NmsClass.getChatSerializerClass(), "a", String.class);
        return ReflectionAPI.invokeMethod(null, a, json);
    }

    public static Object makeChatComponentByFancyMessage(FancyMessage msg)
    {
        return makeChatComponentByJson(msg.toJSONString());
    }

    public static String makeJsonByChatComponent(Object chatComponent)
    {
        if (MineAPI.isGlowstone())
        {
            if (chatComponent.getClass().getSimpleName().equals("TextMessage"))
            {
                Method encode = ReflectionAPI.getMethod(chatComponent, "encode");
                return ReflectionAPI.invokeMethodWithType(chatComponent, encode, String.class);
            }
            else
                return (String) chatComponent;
        }
        Method a = ReflectionAPI.getMethod(ReflectionAPI.NmsClass.getChatSerializerClass(), "a", ReflectionAPI.NmsClass.getIChatBaseComponentClass());
        return ReflectionAPI.invokeMethodWithType(null, a, String.class, chatComponent);
    }
}
