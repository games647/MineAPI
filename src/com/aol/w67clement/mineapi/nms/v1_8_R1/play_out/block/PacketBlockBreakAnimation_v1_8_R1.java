package com.aol.w67clement.mineapi.nms.v1_8_R1.play_out.block;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.PacketPlayOutBlockBreakAnimation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.aol.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.aol.w67clement.mineapi.entity.player.MC_Player;
import com.aol.w67clement.mineapi.enums.PacketType;

/**
 * Packet block break animation for 1.8.R1
 * 
 * @author 67clement
 * 
 */
public class PacketBlockBreakAnimation_v1_8_R1 implements
		PacketBlockBreakAnimation {

	private int entityId;
	private Location blockLocation;
	private byte destroyStage;

	public PacketBlockBreakAnimation_v1_8_R1(MC_Player player,
			Location blockLocation, byte destroyStage) {
		this.entityId = player.getEntityId();
		this.blockLocation = blockLocation;
		this.destroyStage = destroyStage;
	}

	public PacketBlockBreakAnimation_v1_8_R1(MC_Player player, int x, int y,
			int z, byte destroyStage) {
		this(player, new Location(Bukkit.getWorlds().get(0), x, y, z),
				destroyStage);
	}

	public PacketBlockBreakAnimation_v1_8_R1(Player player,
			Location blockLocation, byte destroyStage) {
		this(MineAPI.getNmsManager().getMCPlayer(player), blockLocation,
				destroyStage);
	}

	public PacketBlockBreakAnimation_v1_8_R1(Player player, int x, int y,
			int z, byte destroyStage) {
		this(MineAPI.getNmsManager().getMCPlayer(player), new Location(Bukkit
				.getWorlds().get(0), x, y, z), destroyStage);
	}

	@Override
	public void send(Player player) {
		PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(
				this.entityId, (BlockPosition) BlockPositionWrapper.fromLocation(
						this.blockLocation).toBlockPosition(),
				this.destroyStage);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendAll() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			this.send(player);
		}
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public Location getBlockLocation() {
		return this.blockLocation;
	}

	@Override
	public PacketBlockBreakAnimation setBlockLocation(Location loc) {
		this.blockLocation = loc;
		return this;
	}

	@Override
	public PacketBlockBreakAnimation setBlockLocation(int x, int y, int z) {
		this.blockLocation = new Location(null, x, y, z);
		return this;
	}

	@Override
	public byte getDestroyStage() {
		return this.destroyStage;
	}

	@Override
	public PacketBlockBreakAnimation setDestroyStage(byte destroyStage) {
		this.destroyStage = destroyStage;
		return this;
	}

	@Override
	public int getEntityId() {
		return this.entityId;
	}

	@Deprecated
	@Override
	public PacketBlockBreakAnimation setEntityId(int id) {
		this.entityId = id;
		return this;
	}

	@Override
	public PacketBlockBreakAnimation setEntityId(Player player) {
		return this.setEntityId(MineAPI.getNmsManager().getMCPlayer(player));
	}

	@Override
	public PacketBlockBreakAnimation setEntityId(MC_Player player) {
		return this.setEntityId(player.getEntityId());
	}

}
