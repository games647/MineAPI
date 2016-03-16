package com.w67clement.mineapi.nms.v1_9_R1;

import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.animals.MC_Pig;
import com.w67clement.mineapi.entity.monster.MC_EntityEnderman;
import com.w67clement.mineapi.entity.others.MC_ArmorStand;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.entity.villager.MC_Villager;
import com.w67clement.mineapi.inventory.packets.WindowItems;
import com.w67clement.mineapi.nms.reflection.CraftNmsManager;
import com.w67clement.mineapi.nms.v1_9_R1.entity.*;
import com.w67clement.mineapi.nms.v1_9_R1.packets.play.out.WindowItems_v1_9_R1;
import com.w67clement.mineapi.nms.v1_9_R1.world.MC_World_v1_9_R1;
import com.w67clement.mineapi.nms.v1_9_R1.wrappers.ServerPingWrapper_v1_9_R1;
import com.w67clement.mineapi.world.MC_World;
import java.util.List;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by w67clement on 01/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class NmsManager_v1_9_R1 extends CraftNmsManager
{

    @Override
    public WindowItems getWindowItemsPacket(int windowId, List<ItemStack> items)
    {
        return new WindowItems_v1_9_R1(windowId, items);
    }

    @Override
    public WindowItems getWindowItemsPacket(int windowId, Inventory inventory) {
        return new WindowItems_v1_9_R1(windowId, inventory);
    }

    @Override
    public MC_Entity getMC_Entity(Entity entity)
    {
        return new MC_Entity_v1_9_R1(entity);
    }

    @Override
    public MC_ArmorStand getMC_ArmorStand(ArmorStand armorstand)
    {
        return new MC_ArmorStand_v1_9_R1(armorstand);
    }

    @Override
    public MC_EntityEnderman getMC_EntityEnderman(Enderman enderman)
    {
        return new MC_EntityEnderman_v1_9_R1(enderman);
    }

    @Override
    public MC_Player getMCPlayer(Player player)
    {
        return new MC_Player_v1_9_R1(player);
    }

    @Override
    public MC_Pig getMCPig(Pig pig)
    {
        return new MC_Pig_v1_9_R1(pig);
    }

    @Override
    public MC_Villager getMCVillager(Villager villager)
    {
        return new MC_Villager_v1_9_R1(villager);
    }

    @Override
    public MC_World getMC_World(World world)
    {
        return new MC_World_v1_9_R1(world);
    }

    @Override
    public ServerPingWrapper getServerPingWrapper()
    {
        return getServerPingWrapper(null);
    }

    @Override
    public ServerPingWrapper getServerPingWrapper(Object serverPing)
    {
        return new ServerPingWrapper_v1_9_R1(serverPing);
    }
}
