package com.w67clement.mineapi.nms.v1_8_R1.play_out;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.tab.TabTitle;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;

/**
 * Set a title (header and footer) of the player list Tab.
 * 
 * @author w67clement
 * @version 2.0 - CraftBukkit 1.8
 */
public class TabTitle_v1_8_R1 implements TabTitle
{

	private String header;
	private String footer;

	public TabTitle_v1_8_R1(String header, String footer) {
		this.header = header;
		this.footer = footer;
	}

	@Override
	public void send(Player player)
	{
		if (this.header == null) this.header = "";
		this.header = ChatColor.translateAlternateColorCodes('&', this.header);
		if (this.footer == null) this.footer = "";
		this.footer = ChatColor.translateAlternateColorCodes('&', this.footer);
		IChatBaseComponent tabHeader = ChatSerializer.a("{text:\"" + this.header + ChatColor.RESET + "\"}");
		IChatBaseComponent tabFooter = ChatSerializer.a("{text:\"" + this.footer + ChatColor.RESET + "\"}");
		PacketPlayOutPlayerListHeaderFooter tabTitlePacket = new PacketPlayOutPlayerListHeaderFooter(tabHeader);

		try
		{
			Field field = tabTitlePacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(tabTitlePacket, tabFooter);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(tabTitlePacket);
		}
	}

	@Override
	public void sendAll()
	{
		for (Player players : Bukkit.getOnlinePlayers())
		{
			this.send(players);
		}
	}

	@Override
	public String getHeader()
	{
		return this.header;
	}

	@Override
	public String getFooter()
	{
		return this.footer;
	}

	@Override
	public TabTitle setHeader(String header)
	{
		this.header = header;
		return this;
	}

	@Override
	public TabTitle setFooter(String footer)
	{
		this.footer = footer;
		return this;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}
}

// End of TabTitle class