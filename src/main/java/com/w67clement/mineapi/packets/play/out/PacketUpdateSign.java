package com.w67clement.mineapi.packets.play.out;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.message.FancyMessage;
import com.w67clement.mineapi.nms.PacketSender;
import org.bukkit.Location;
import org.bukkit.block.Sign;

/**
 * Created by w67clement on 13/02/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public abstract class PacketUpdateSign extends PacketSender
{
    protected Location location;
    protected String[] contents = new String[4];

    public PacketUpdateSign(Sign sign)
    {
        this.location = sign.getLocation();
        // this.contents = sign.getLines();
    }

    public PacketUpdateSign(Location location, String[] contents)
    {
        this.location = location;
        this.contents = contents;
    }

    public PacketUpdateSign(int x, int y, int z, String[] contents)
    {
        this.location = new Location(null, x, y, z);
        this.contents = contents;
    }

    /**
     * Gets the location of the sign will be updated.
     *
     * @return Location of the sign.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Sets the location of the sign will be updated. <br></br>
     * <b>Note:</b> The method can't change the place of a sign.
     *
     * @param location Location of the sign.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * Check if the sign is in the location.
     *
     * @return If the location is valid or not.
     */
    public boolean isValidLocation()
    {
        return this.isValidLocation(this.location);
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
        if (location.getBlock().getState() != null)
            if (location.getBlock().getState() instanceof Sign)
                return true;
        return false;
    }

    /**
     * Gets the contents of the sign.
     *
     * @return Array of 4 String formatted in Json.
     */
    public String[] getContents()
    {
        return contents;
    }

    /**
     * Sets the contents of the sign. <br></br>
     * <b>Note:</b> The method don't change the real contents of the sign.
     *
     * @param contents New contents of the sign formatted in Json.
     */
    public void setContents(String[] contents)
    {
        this.contents = contents;
    }

    /**
     * Gets content at a line. <br></br>
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
        return contents[line];
    }

    /**
     * Sets content at a line. <br></br>
     * <b>Note:</b> The method don't change the real content of the sign.
     *
     * @param line    Line of the content.
     * @param content New content at the line formatted in Json.
     */
    public void setLine(int line, String content)
    {
        this.contents[line] = content;
    }

    /**
     * Sets content at a line. <br></br>
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
