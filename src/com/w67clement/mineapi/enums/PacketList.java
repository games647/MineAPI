package com.w67clement.mineapi.enums;

public enum PacketList {

	ALL("ALL", null),
	//Packet play out
	PacketPlayOutAbilities("PacketPlayOutAbilities", PacketType.PACKETPLAYOUT),
	PacketPlayOutAnimation("PacketPlayOutAnimation", PacketType.PACKETPLAYOUT),
	PacketPlayOutAttachEntity("PacketPlayOutAttachEntity", PacketType.PACKETPLAYOUT),
	PacketPlayOutBed("PacketPlayOutBed", PacketType.PACKETPLAYOUT),
	PacketPlayOutBlockAction("PacketPlayOutBlockAction", PacketType.PACKETPLAYOUT),
	PacketPlayOutBlockBreakAnimation("PacketPlayOutBlockBreakAnimation", PacketType.PACKETPLAYOUT),
	PacketPlayOutBlockChange("PacketPlayOutBlockChange", PacketType.PACKETPLAYOUT),
	PacketPlayOutCamera("PacketPlayOutCamera", PacketType.PACKETPLAYOUT, MinecraftVersion.v1_8_R1),
	PacketPlayOutChat("PacketPlayOutChat", PacketType.PACKETPLAYOUT),
	PacketPlayOutCloseWindow("PacketPlayOutCloseWindow", PacketType.PACKETPLAYOUT),
	PacketPlayOutCollect("PacketPlayOutCollect", PacketType.PACKETPLAYOUT),
	PacketPlayOutCombatEvent("PacketPlayOutCombatEvent", PacketType.PACKETPLAYOUT),
	PacketPlayOutCustomPayload("PacketPlayOutCustomPayload", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntity("PacketPlayOutEntity", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntityDestroy("PacketPlayOutEntityDestroy", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntityEffect("PacketPlayOutEntityEffect", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntityEquipment("PacketPlayOutEntityEquipment", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntityHeadRotation("PacketPlayOutEntityHeadRotation", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntityLook("PacketPlayOutEntityLook", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntityMetadata("PacketPlayOutEntityMetadata", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntityStatus("PacketPlayOutEntityStatus", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntityTeleport("PacketPlayOutEntityTeleport", PacketType.PACKETPLAYOUT),
	PacketPlayOutEntityVelocity("PacketPlayOutEntityVelocity", PacketType.PACKETPLAYOUT),
	PacketPlayOutExperience("PacketPlayOutExperience", PacketType.PACKETPLAYOUT),
	PacketPlayOutExplosion("PacketPlayOutExplosion", PacketType.PACKETPLAYOUT),
	PacketPlayOutGameStateChange("PacketPlayOutGameStateChange", PacketType.PACKETPLAYOUT),
	PacketPlayOutHeldItemSlot("PacketPlayOutHeldItemSlot", PacketType.PACKETPLAYOUT),
	PacketPlayOutKeepAlive("PacketPlayOutKeepAlive", PacketType.PACKETPLAYOUT),
	PacketPlayOutKickDisconnect("PacketPlayOutKickDisconnect", PacketType.PACKETPLAYOUT),
	PacketPlayOutLogin("PacketPlayOutLogin", PacketType.PACKETPLAYOUT),
	PacketPlayOutMap("PacketPlayOutMap", PacketType.PACKETPLAYOUT),
	PacketPlayOutMapChunk("PacketPlayOutMapChunk", PacketType.PACKETPLAYOUT),
	PacketPlayOutMapChunkBulk("PacketPlayOutMapChunkBulk", PacketType.PACKETPLAYOUT),
	PacketPlayOutMultiBlockChange("PacketPlayOutMultiBlockChange", PacketType.PACKETPLAYOUT),
	PacketPlayOutNamedEntitySpawn("PacketPlayOutNamedEntitySpawn", PacketType.PACKETPLAYOUT),
	PacketPlayOutNamedSoundEffect("PacketPlayOutNamedSoundEffect", PacketType.PACKETPLAYOUT),
	PacketPlayOutOpenSignEditor("PacketPlayOutOpenSignEditor", PacketType.PACKETPLAYOUT),
	PacketPlayOutOpenWindow("PacketPlayOutOpenWindow", PacketType.PACKETPLAYOUT),
	PacketPlayOutPlayerInfo("PacketPlayOutPlayerInfo", PacketType.PACKETPLAYOUT),
	PacketPlayOutPlayerListHeaderFooter("PacketPlayOutPlayerListHeaderFooter", PacketType.PACKETPLAYOUT, MinecraftVersion.v1_8_R1),
	PacketPlayOutPosition("PacketPlayOutPosition", PacketType.PACKETPLAYOUT),
	PacketPlayOutRelEntityMove("PacketPlayOutRelEntityMove", PacketType.PACKETPLAYOUT),
	PacketPlayOutRelEntityMoveLook("PacketPlayOutRelEntityMoveLook", PacketType.PACKETPLAYOUT),
	PacketPlayOutRemoveEntityEffect("PacketPlayOutRemoveEntityEffect", PacketType.PACKETPLAYOUT),
	PacketPlayOutResourcePackSend("PacketPlayOutResourcePackSend", PacketType.PACKETPLAYOUT),
	PacketPlayOutRespawn("PacketPlayOutRespawn", PacketType.PACKETPLAYOUT),
	PacketPlayOutScoreboardDisplayObjective("PacketPlayOutScoreboardDisplayObjective", PacketType.PACKETPLAYOUT),
	PacketPlayOutScoreboardObjective("PacketPlayOutScoreboardObjective", PacketType.PACKETPLAYOUT),
	PacketPlayOutScoreboardScore("PacketPlayOutScoreboardScore", PacketType.PACKETPLAYOUT),
	PacketPlayOutScoreboardTeam("PacketPlayOutScoreboardTeam", PacketType.PACKETPLAYOUT),
	PacketPlayOutServerDifficulty("PacketPlayOutServerDifficulty", PacketType.PACKETPLAYOUT),
	PacketPlayOutSetCompression("PacketPlayOutSetCompression", PacketType.PACKETPLAYOUT),
	PacketPlayOutSetSlot("PacketPlayOutSetSlot", PacketType.PACKETPLAYOUT),
	PacketPlayOutSpawnEntity("PacketPlayOutSpawnEntity", PacketType.PACKETPLAYOUT),
	PacketPlayOutSpawnEntityExperienceOrb("PacketPlayOutSpawnEntityExperienceOrb", PacketType.PACKETPLAYOUT),
	PacketPlayOutSpawnEntityLiving("PacketPlayOutSpawnEntityLiving", PacketType.PACKETPLAYOUT),
	PacketPlayOutSpawnEntityPainting("PacketPlayOutSpawnEntityPainting", PacketType.PACKETPLAYOUT),
	PacketPlayOutSpawnEntityWeather("PacketPlayOutSpawnEntityWeather", PacketType.PACKETPLAYOUT),
	PacketPlayOutSpawnPosition("PacketPlayOutSpawnPosition", PacketType.PACKETPLAYOUT),
	PacketPlayOutStatistic("PacketPlayOutStatistic", PacketType.PACKETPLAYOUT),
	PacketPlayOutTabComplete("PacketPlayOutTabComplete", PacketType.PACKETPLAYOUT),
	PacketPlayOutTileEntityData("PacketPlayOutTileEntityData", PacketType.PACKETPLAYOUT),
	PacketPlayOutTitle("PacketPlayoutTitle", PacketType.PACKETPLAYOUT, MinecraftVersion.v1_8_R1),
	PacketPlayOutTransaction("PacketPlayOutTransaction", PacketType.PACKETPLAYOUT),
	PacketPlayOutUpdateAttributes("PacketPlayOutUpdateAttributes", PacketType.PACKETPLAYOUT),
	PacketPlayOutUpdateEntityNBT("PacketPlayOutUpdateEntityNBT", PacketType.PACKETPLAYOUT),
	PacketPlayOutUpdateHealth("PacketPlayOutUpdateHealth", PacketType.PACKETPLAYOUT),
	PacketPlayOutUpdateSign("PacketPlayOutUpdateSign", PacketType.PACKETPLAYOUT),
	PacketPlayOutUpdateTime("PacketPlayOutUpdateTime", PacketType.PACKETPLAYOUT),
	PacketPlayOutWindowData("PacketPlayOutWindowData", PacketType.PACKETPLAYOUT),
	PacketPlayOutWindowItems("PacketPlayOutWindowItems", PacketType.PACKETPLAYOUT),
	PacketPlayOutWorldBorder("PacketPlayOutWorldBorder", PacketType.PACKETPLAYOUT, MinecraftVersion.v1_8_R1),
	PacketPlayOutWorldEvent("PacketPlayOutWorldEvent", PacketType.PACKETPLAYOUT),
	PacketPlayOutWorldParticles("PacketPlayOutWorldParticles", PacketType.PACKETPLAYOUT),
	//Packet play in
	PacketPlayInAbilities("PacketPlayInAbilities", PacketType.PACKETPLAYIN),
	PacketPlayInArmAnimation("PacketPlayInArmAnimation", PacketType.PACKETPLAYIN),
	PacketPlayInBlockDig("PacketPlayInBlockDig", PacketType.PACKETPLAYIN),
	PacketPlayInBlockPlace("PacketPlayInBlockPlace", PacketType.PACKETPLAYIN),
	PacketPlayInChat("PacketPlayInChat", PacketType.PACKETPLAYIN),
	PacketPlayInClientCommand("PacketPlayInClientCommand", PacketType.PACKETPLAYIN),
	PacketPlayInCloseWindow("PacketPlayInCloseWindow", PacketType.PACKETPLAYIN),
	PacketPlayInCustomPayload("PacketPlayInCustomPayload", PacketType.PACKETPLAYIN),
	PacketPlayInEnchantItem("PacketPlayInEnchantItem", PacketType.PACKETPLAYIN),
	PacketPlayInEntityAction("PacketPlayInEntityAction", PacketType.PACKETPLAYIN),
	PacketPlayInFlying("PacketPlayInFlying", PacketType.PACKETPLAYIN),
	PacketPlayInHeldItemSlot("PacketPlayInHeldItemSlot", PacketType.PACKETPLAYIN),
	PacketPlayInKeepAlive("PacketPlayInKeepAlive", PacketType.PACKETPLAYIN),
	PacketPlayInLook("PacketPlayInLook", PacketType.PACKETPLAYIN),
	PacketPlayInPosition("PacketPlayInPosition", PacketType.PACKETPLAYIN),
	PacketPlayInPositionLook("PacketPlayInPositionLook", PacketType.PACKETPLAYIN),
	PacketPlayInResourcePackStatus("PacketPlayInResourcePackStatus", PacketType.PACKETPLAYIN),
	PacketPlayInSetCreativeSlot("PacketPlayInSetCreativeSlot", PacketType.PACKETPLAYIN),
	PacketPlayInSettings("PacketPlayInSettings", PacketType.PACKETPLAYIN),
	PacketPlayInSpectate("PacketPlayInSpectate", PacketType.PACKETPLAYIN),
	PacketPlayInSteerVehicle("PacketPlayInSteerVehicle", PacketType.PACKETPLAYIN),
	PacketPlayInTabComplete("PacketPlayInTabComplete", PacketType.PACKETPLAYIN),
	PacketPlayInTransaction("PacketPlayInTransaction", PacketType.PACKETPLAYIN),
	PacketPlayInUpdateSign("PacketPlayInUpdateSign", PacketType.PACKETPLAYIN),
	PacketPlayInUseEntity("PacketPlayInUseEntity", PacketType.PACKETPLAYIN),
	PacketPlayInWindowClick("PacketPlayInWindowClick", PacketType.PACKETPLAYIN),
	
	
	PacketStatusInPing("PacketStatusInPing", PacketType.PACKETSTATUS),
	PacketStatusInStart("PacketStatusInStart", PacketType.PACKETSTATUS),
	
	PacketStatusOutPong("PacketStatusOutPong", PacketType.PACKETSTATUS),
	PacketStatusOutServerInfo("PacketStatusOutServerInfo", PacketType.PACKETSTATUS);
	
	private String packetName;
	private PacketType packetType;
	private MinecraftVersion packetVersion;
	
	private PacketList(String packetName, PacketType packetType) {
		this.packetName = packetName;
		this.packetType = packetType;
		this.packetVersion = MinecraftVersion.UNKNOW;
	}
	
	private PacketList(String packetName, PacketType packetType, MinecraftVersion packetVersion) {
		this.packetName = packetName;
		this.packetType = packetType;
		this.packetVersion = packetVersion;
	}
	
	public String getPacketName() {
		return this.packetName;
	}
	
	public PacketType getPacketType() {
		return this.packetType;
	}
	
	public MinecraftVersion getPacketVersion() {
		return this.packetVersion;
	}
}
