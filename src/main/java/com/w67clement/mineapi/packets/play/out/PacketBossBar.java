package com.w67clement.mineapi.packets.play.out;

import com.google.gson.JsonObject;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

/**
 * Created by w67clement on 17/02/2016.
 * <p>
 * Class of project: MineAPI
 */
public abstract class PacketBossBar<T> extends PacketSender<T>
{
    protected UUID uuid;
    protected BossBarAction action;
    protected BossBarData data;

    public PacketBossBar(T packet)
    {
        super(packet);
    }

    /**
     * Gets the Unique ID for this bar.
     *
     * @return Unique ID.
     */
    public UUID getUuid()
    {
        return uuid;
    }

    /**
     * Sets the Unique ID for this bar.
     *
     * @param uuid Unique ID.
     */
    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    /**
     * Gets the action for determines the layout of the remaining packet
     *
     * @return Action on the layout of this bar.
     */
    public BossBarAction getAction()
    {
        return action;
    }

    /**
     * Determines the layout of the remaining packet.
     *
     * @param action Action on the layout of this bar.
     */
    public void setAction(BossBarAction action)
    {
        this.action = action;
    }

    /**
     * Gets the data of this bar.
     *
     * @return Data of this bar.
     */
    public BossBarData getData()
    {
        return data;
    }

    /**
     * Sets the data for this bar.
     *
     * @param data Data for this bar.
     */
    public void setData(BossBarData data)
    {
        this.data = data;
    }

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }

    public enum BossBarAction
    {
        ADD(0),
        REMOVE(1),
        UPDATE_HEALTH(2),
        UPDATE_TITLE(3),
        UPDATE_STYLE(4),
        UPDATE_FLAGS(5);

        int id;

        BossBarAction(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return id;
        }
    }

    /**
     * BossBarData contains all data of a bar:
     * <ul>
     * <li>Title</li>
     * <li>Health</li>
     * <li>Color</li>
     * <li>Division</li>
     * <li>Flags</li>
     * </ul>
     */
    public class BossBarData
    {
        private String title;
        private float health;
        private BarColor color;
        private BarStyle style;
        private Set<BarFlag> flags = EnumSet.noneOf(BarFlag.class);

        public BossBarData(String title, float health, BarColor color, BarStyle style, BarFlag... flags)
        {
            this.title = title;
            this.health = health;
            this.color = color;
            this.style = style;
            if (flags.length > 0)
                Collections.addAll(this.flags, flags);
        }

        public BossBarData(BossBar bar)
        {
            this.setTitleText(bar.getTitle());
            this.health = (float) bar.getProgress();
            this.color = bar.getColor();
            this.style = bar.getStyle();
            Set<BarFlag> bukkitFlags = (Set<BarFlag>) ReflectionAPI.getValueWithType(bar, ReflectionAPI.getField(bar.getClass(), "flags", true), Set.class);
            assert bukkitFlags != null : "Error: [{\"class\":\"PacketBossBar$BossBarData\",\"method\":\"new::(BossBar)\",\"line\":149,\"error\":\"Error when reading flags with Bukkit BossBar.\"}], please contact author and report the bug.";
            bukkitFlags.forEach(this.flags::add);
        }

        /**
         * Returns the progress of the bar between 0.0 and 1.0.
         *
         * @return The progress of the bar.
         */
        public float getHealth()
        {
            return health;
        }

        /**
         * Sets the progress of the bar. Values should be between 0.0 (empty) and 1.0 (full)
         *
         * @param health The progress of the bar.
         */
        public void setHealth(float health)
        {
            this.health = health;
        }

        /**
         * Returns the title of this boss bar in Json.
         *
         * @return The title of the bar in Json.
         */
        public String getTitle()
        {
            return title;
        }

        /**
         * Sets the title of this boss bar.
         *
         * @param title The title of the bar in Json.
         */
        public void setTitle(String title)
        {
            this.title = title;
        }

        /**
         * Sets the title of this boss bar.
         *
         * @param title The title of the bar.
         */
        public void setTitleText(String title)
        {
            JsonObject json = new JsonObject();
            json.addProperty("text", title);
            this.title = json.toString();
        }

        /**
         * Returns the color of this boss bar.
         *
         * @return The color of the bar.
         */
        public BarColor getColor()
        {
            return color;
        }

        /**
         * Sets the color of this boss bar.
         *
         * @param color The color of the bar.
         */
        public void setColor(BarColor color)
        {
            this.color = color;
        }


        /**
         * Returns the style of this boss bar.
         *
         * @return The style of the bar.
         */
        public BarStyle getStyle()
        {
            return style;
        }

        public void setStyle(BarStyle style)
        {
            this.style = style;
        }

        /**
         * Returns the flags of this boss bar.
         *
         * @return The flags of the bar.
         */
        public Set<BarFlag> getFlags()
        {
            return flags;
        }

        /**
         * Add an optional flag to this boss bar.
         *
         * @param flag An optional flag to set on the boss bar.
         */
        public void addFlag(BarFlag flag)
        {
            this.flags.add(flag);
        }

        /**
         * Remove an existing flag on this boss bar.
         *
         * @param flag The existing flag to remove.
         */
        public void removeFlag(BarFlag flag)
        {
            this.flags.remove(flag);
        }

        /**
         * Returns whether this boss bar as the passed flag set.
         *
         * @param flag The flag to check.
         *
         * @return Whether it has the flag.
         */
        public boolean hasFlag(BarFlag flag)
        {
            return this.flags.contains(flag);
        }
    }
}
