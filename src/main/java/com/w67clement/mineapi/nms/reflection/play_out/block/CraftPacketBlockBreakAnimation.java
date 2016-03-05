package com.w67clement.mineapi.nms.reflection.play_out.block;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.enums.PacketType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CraftPacketBlockBreakAnimation extends PacketBlockBreakAnimation
{

	public CraftPacketBlockBreakAnimation(MC_Player player,
			Location blockLocation, byte destroyStage) {
		super(player, blockLocation, destroyStage);
	}

	public CraftPacketBlockBreakAnimation(MC_Player player, int x, int y, int z,
			byte destroyStage) {
		super(player, new Location(Bukkit.getWorlds().get(0), x, y, z),
				destroyStage);
	}

	public CraftPacketBlockBreakAnimation(Player player, Location blockLocation,
			byte destroyStage) {
		super(player, blockLocation, destroyStage);
	}

	public CraftPacketBlockBreakAnimation(Player player, int x, int y, int z,
			byte destroyStage) {
		super(player, new Location(Bukkit.getWorlds().get(0), x, y, z),
				destroyStage);
	}

	@Override
	public void send(Player player)
	{
		// Send the packet.
		ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public Object constructPacket()
	{
		if (MineAPI.isSpigot())
		{
			return this.constructPacket_Bukkit();
		}
		else if (MineAPI
				.isGlowstone()) { return this.constructPacket_Glowstone(); }
		return this.constructPacket_Bukkit();
	}

	private Object constructPacket_Bukkit()
	{
		Class<?> packetClass = ReflectionAPI
				.getNmsClass("PacketPlayOutBlockBreakAnimation");
		// Create packet
		return ReflectionAPI.newInstance(
				ReflectionAPI.getConstructor(packetClass, int.class,
						ReflectionAPI.getNmsClass("BlockPosition"), int.class),
				this.entityId, BlockPositionWrapper
						.fromLocation(this.blockLocation).toBlockPosition(),
				this.destroyStage);
	}

	private Object constructPacket_Glowstone()
	{
		throw new UnsupportedOperationException(
				"[" + this.getClass().getSimpleName()
						+ "] Glowstone don't support this packet!");
	}

}
