package com.w67clement.mineapi.api.wrappers;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.enums.mc.MC_ChatVisibility;

public class ChatVisibilityWrapper
{

    public static Object chatVisibilityToEnumChatVisibility(MC_ChatVisibility chatVisibility)
    {
        return ReflectionAPI.invokeMethod(null, ReflectionAPI.getMethod(ReflectionAPI.NmsClass.getEnumChatVisibilityClass(), "valueOf", false, String.class), chatVisibility.toString());
    }

    public static MC_ChatVisibility makeMCChatVisibilityByEnumChatVisibility(Object chatVisibility)
    {
        return MC_ChatVisibility.valueOf(chatVisibility.toString());
    }
}
