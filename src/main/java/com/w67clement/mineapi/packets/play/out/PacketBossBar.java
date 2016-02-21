package com.w67clement.mineapi.packets.play.out;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;
import java.util.UUID;

/**
 * Created by w67clement on 17/02/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public abstract class PacketBossBar extends PacketSender
{
    protected UUID uuid;
    protected BossBarAction action;
    protected BossBarData data;

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

        /**
         * Gets the health of this bar.
         *
         * @return Health of the bar.
         */
        public float getHealth()
        {
            return health;
        }
    }
}
