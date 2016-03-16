package com.w67clement.mineapi.packets.play.out;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.message.FancyMessage;
import com.w67clement.mineapi.nms.PacketSender;
import org.bukkit.Location;
import org.bukkit.block.Sign;

/**
 * Created by w67clement on 13/02/2016.
 * <p>
 * Class of project: MineAPI
 */
public abstract class PacketUpdateSign<T> extends PacketSender<T>
{

    public PacketUpdateSign(T packet)
    {
        super(packet);
    }

    /**
     * Gets the location of the sign will be updated.
     *
     * @return Location of the sign.
     */
    public abstract Location getLocation();

    /**
     * Sets the location of the sign will be updated. <br>
     * <b>Note:</b> The method can't change the place of a sign.
     *
     * @param location Location of the sign.
     */
    public abstract void setLocation(Location location);

    /**
     * Check if the sign is in the location.
     *
     * @return If the location is valid or not.
     */
    public boolean isValidLocation()
    {
        return this.isValidLocation(this.getLocation());
    }

    /**
     * Check if a sign is in the location.
     *
     * @param location The location will be checked.
     *
     * @return If the location is valid or not.
     */
    public boolean isValidLocation(Location location)
    {
        if (getLocation().getBlock().getState() != null)
            if (getLocation().getBlock().getState() instanceof Sign)
                return true;
        return false;
    }

    /**
     * Gets the contents of the sign.
     *
     * @return Array of 4 String formatted in Json.
     */
    public abstract String[] getContents();

    /**
     * Sets the contents of the sign. <br>
     * <b>Note:</b> The method don't change the real contents of the sign.
     *
     * @param contents New contents of the sign formatted in Json.
     */
    public abstract void setContents(String[] contents);

    /**
     * Gets content at a line. <br>
     * <b>Note:</b> The method don't change the real content of the sign.
     *
     * @param line Line of the content.
     *
     * @return Content at the line.
     */
    public String getLine(int line)
    {
        if (line < 0)
            line = 0;
        if (line > 3)
            line = 3;
        return getContents()[line];
    }

    /**
     * Sets content at a line. <br>
     * <b>Note:</b> The method don't change the real content of the sign.
     *
     * @param line    Line of the content.
     * @param content New content at the line formatted in Json.
     */
    public void setLine(int line, String content)
    {
        String[] contents = this.getContents();
        contents[line] = content;
        this.setContents(contents);
    }

    /**
     * Sets content at a line. <br>
     * <b>Note:</b> The method don't change the real content of the sign.
     *
     * @param line    Line of the content.
     * @param content New content using FancyMessage.
     */
    public void setLine(int line, FancyMessage content)
    {
        this.setLine(line, content.toJSONString());
    }

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }
}
