package com.w67clement.mineapi;

import com.w67clement.mineapi.nms.NmsManager;
import com.w67clement.mineapi.system.ServerType;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by w67clement on 20/02/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class BukkitMineAPI extends JavaPlugin implements MinePlugin
{
    private MineAPI mineapi;
    private ServerType serverType;
    private NmsManager nms;

    @Override
    public void sendMessageToConsole(String message)
    {

    }

    @Override
    public void sendMessageToConsole(String message, boolean debug)
    {

    }

    @Override
    public NmsManager getNmsManager()
    {
        return this.nms;
    }

    @Override
    public ServerType getServerType()
    {
        return this.serverType;
    }

    @Override
    public void onLoad() {
        this.mineapi = new MineAPI();
        this.mineapi.onLoad();
    }

    @Override
    public void onEnable() {
        this.mineapi.onEnable();
    }

    @Override
    public void onDisable() {
        this.mineapi.onDisable();
    }
}
