package com.w67clement.mineapi.enums;

import com.google.common.collect.Maps;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import java.util.Map;
import org.apache.commons.lang.Validate;

public enum MinecraftVersion
{

    UNKNOWN("Unknown", -1),
    OLD("1.6.x and old", -1),
    v1_7_R1("1.7.2", 4),
    v1_7_4("1.7.4", 4),
    v1_7_5("1.7.5", 4),
    v1_7_6("1.7.6", 5),
    v1_7_7("1.7.7", 5),
    v1_7_8("1.7.8", 5),
    v1_7_9("1.7.9", 5),
    v1_7_R4("1.7.10", 5),
    v1_8_R1("1.8", 47),
    v1_8_1("1.8.1", 47),
    v1_8_2("1.8.2", 47),
    v1_8_R2("1.8.3", 47),
    v1_8_R3("1.8.8", 47),
    v1_9_R1("1.9.2", 109),
    v1_9_R2("1.9.4", 110);

    private final static Map<String, MinecraftVersion> BY_Version = Maps.newHashMap();

    static
    {
        for (MinecraftVersion mcVersion : values())
        {
            BY_Version.put(mcVersion.version, mcVersion);
        }
    }

    private String version = "1.0.0";
    private int protocol = 0;

    MinecraftVersion(String version, int protocol)
    {
        this.version = version;
        this.protocol = protocol;
    }

    public static MinecraftVersion getServerVersion()
    {
        switch (MineAPI.getServerVersion())
        {
            case "v1_7_R4":
                return v1_7_R4;
            case "v1_8_R1":
                return v1_8_R1;
            case "v1_8_R2":
                return v1_8_R2;
            case "v1_8_R3":
                return v1_8_R3;
            case "v1_9_R1":
                return v1_9_R1;
            case "v1_9_R2":
                return v1_9_R2;
        }
        if (MineAPI.getServerVersion().contains("Glowstone"))
        {
            Class<?> glowServer = ReflectionAPI.getClass("net.glowstone.GlowServer");
            String game_version = (String) ReflectionAPI.getValue(null, ReflectionAPI.getField(glowServer, "GAME_VERSION", true));
            return getByVersion(game_version);
        }
        else if (MineAPI.isRainbow())
        {
            Class<?> joeUtils = ReflectionAPI.getClass("joebkt._JoeUtils");
            String game_version = (String) ReflectionAPI.getValue(null, ReflectionAPI.getField(joeUtils, "MC_VERSION_STRING", true));
            return getByVersion(game_version);
        }
        return UNKNOWN;
    }

    public static MinecraftVersion getByVersion(String version)
    {
        Validate.notNull(version, "Version cannot be null!");
        return BY_Version.get(version);
    }

    public String getVersion()
    {
        return this.version;
    }

    public int getProtocolVersion()
    {
        return this.protocol;
    }

    public String toString()
    {
        return this.version;
    }
}
