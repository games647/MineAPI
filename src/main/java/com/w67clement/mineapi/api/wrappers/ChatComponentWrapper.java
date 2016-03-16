package com.w67clement.mineapi.api.wrappers;

import com.google.gson.JsonObject;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI.*;
import com.w67clement.mineapi.message.FancyMessage;
import java.lang.reflect.Method;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

public class ChatComponentWrapper
{
    private static final Class<?> chatSerializerClass = NmsClass.getChatSerializerClass();
    private static final Method toChatComponentMethod = getMethod(chatSerializerClass, "a", String.class);
    private static final Method toJsonMethod = getMethod(chatSerializerClass, "a", NmsClass.getIChatBaseComponentClass());
    ;

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
        return invokeMethod(null, toChatComponentMethod, json);
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
                Method encode = getMethod(chatComponent, "encode");
                return invokeMethodWithType(chatComponent, encode, String.class);
            }
            else
                return (String) chatComponent;
        }
        return invokeMethodWithType(null, toJsonMethod, String.class, chatComponent);
    }
}
