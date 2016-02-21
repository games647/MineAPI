package com.w67clement.mineapi;

import com.w67clement.mineapi.nms.NmsManager;
import com.w67clement.mineapi.system.ServerType;

/**
 * Created by w67clement on 20/02/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public interface MinePlugin
{

    void sendMessageToConsole(String message);

    void sendMessageToConsole(String message, boolean debug);

    NmsManager getNmsManager();

    ServerType getServerType();

    void onLoad();

    void onEnable();

    void onDisable();
}
