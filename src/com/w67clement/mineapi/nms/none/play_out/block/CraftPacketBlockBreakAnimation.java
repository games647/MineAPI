package com.w67clement.mineapi.nms.none.play_out.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.enums.PacketType;

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
		// Get packet class
		Class<?> packetClass = ReflectionAPI
				.getNmsClass("PacketPlayOutBlockBreakAnimation");
		// Create packet
		Object packet = ReflectionAPI.newInstance(
				ReflectionAPI.getConstructor(packetClass, int.class,
						ReflectionAPI.getNmsClass("BlockPosition"), int.class),
				this.entityId, BlockPositionWrapper
						.fromLocation(this.blockLocation).toBlockPosition(),
				this.destroyStage);
		// Send the packet.
		ReflectionAPI.NmsClass.sendPacket(player, packet);
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

}
