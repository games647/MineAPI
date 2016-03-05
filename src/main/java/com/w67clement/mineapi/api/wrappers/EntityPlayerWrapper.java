package com.w67clement.mineapi.api.wrappers;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.entity.player.MC_Player;
import org.bukkit.entity.Player;

public class EntityPlayerWrapper
{

    public static Object makeEntityPlayerByPlayer(Player player)
    {
        return ReflectionAPI.NmsClass.getEntityPlayerByPlayer(player);
    }

    public static Object makeEntityPlayerByMCPlayer(MC_Player player)
    {
        return player.getMC_Handle();
    }

    public static Player makePlayerByEntityPlayer(Object entityPlayer)
    {
            if (entityPlayer.getClass().equals(ReflectionAPI.getNmsClass("EntityPlayer")))
            {
                return (Player) ReflectionAPI.invokeMethod(entityPlayer, ReflectionAPI.getMethod(entityPlayer, "getBukkitEntity"));
            }
            return null;

    }

    public static MC_Player makeMC_PlayerByEntityPlayer(Object entityPlayer)
    {
        return MineAPI.getNmsManager().getMCPlayer(makePlayerByEntityPlayer(entityPlayer));
    }

}