package com.w67clement.mineapi.nms.v1_9_R1.packets.play.out;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.packets.play.out.PacketBossBar;
import net.minecraft.server.v1_9_R1.BossBattle;
import net.minecraft.server.v1_9_R1.BossBattleServer;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutBoss;
import net.minecraft.server.v1_9_R1.PacketPlayOutBoss.Action;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

/**
 * Created by w67clement on 05/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class PacketBossBar_v1_9_R1 extends PacketBossBar
{
    @Override
    public void send(Player player)
    {
        ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
    }

    @Override
    public Object constructPacket()
    {
        BossBattle bar = new BossBattleServer((IChatBaseComponent) ChatComponentWrapper.makeChatComponentByJson(this.data.getTitle()), BossBattle.BarColor.valueOf(this.data.getColor().name()), this.convertStyle(this.data.getStyle()));
        bar.a(this.data.hasFlag(BarFlag.DARKEN_SKY));
        bar.b(this.data.hasFlag(BarFlag.PLAY_BOSS_MUSIC));
        bar.c(this.data.hasFlag(BarFlag.CREATE_FOG));
        PacketPlayOutBoss.Action action = Action.values()[this.action.getId()];
        return new PacketPlayOutBoss(action, bar);
    }

    private net.minecraft.server.v1_9_R1.BossBattle.BarStyle convertStyle(BarStyle style)
    {
        switch (style.ordinal())
        {
            case 1:
            default:
                return net.minecraft.server.v1_9_R1.BossBattle.BarStyle.PROGRESS;
            case 2:
                return net.minecraft.server.v1_9_R1.BossBattle.BarStyle.NOTCHED_6;
            case 3:
                return net.minecraft.server.v1_9_R1.BossBattle.BarStyle.NOTCHED_10;
            case 4:
                return net.minecraft.server.v1_9_R1.BossBattle.BarStyle.NOTCHED_12;
            case 5:
                return net.minecraft.server.v1_9_R1.BossBattle.BarStyle.NOTCHED_20;
        }
    }
}
