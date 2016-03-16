package com.w67clement.mineapi.message;

import com.w67clement.mineapi.MineAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Displays message in the action bar message of the player!
 *
 * @author w67clement
 */
public class ActionBarMessage
{

    protected String message;

    public ActionBarMessage(String message)
    {
        this.setMessage(message);
    }

    /**
     * Gets the message will be displayed in the action bar.
     *
     * @return The message.
     */
    public String getMessage()
    {
        return this.message;
    }

    /**
     * Define the message will be displayed in the action bar.
     *
     * @param actionBarMessage An sample text.
     *
     * @return Instance.
     */
    public ActionBarMessage setMessage(String actionBarMessage)
    {
        if (actionBarMessage != null)
            this.message = actionBarMessage;
        else
            this.message = "";
        return this;
    }

    public void send(Player player)
    {
        MineAPI.getNmsManager().getPacketChat("[{\"text\":\"" + this.message + "\"}]", (byte) 2).send(player);
    }

    public void sendAll()
    {
        Bukkit.getOnlinePlayers().forEach(this::send);
    }

    public Object constructPacket()
    {
        return MineAPI.getNmsManager().getPacketChat("[{\"text\":\"" + this.message + "\"}]", (byte) 2).constructPacket();
    }

}
