package com.w67clement.mineapi.enums;

import com.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.w67clement.mineapi.entity.player.ClientCommand;
import com.w67clement.mineapi.inventory.packets.WindowItems;
import com.w67clement.mineapi.inventory.packets.WindowOpen;
import com.w67clement.mineapi.message.PacketChat;
import com.w67clement.mineapi.nms.NmsPacket;
import com.w67clement.mineapi.packets.handshake.PacketHandshake;
import com.w67clement.mineapi.packets.play.out.PacketUpdateSign;
import com.w67clement.mineapi.tab.TabTitle;
import com.w67clement.mineapi.world.PacketExplosion;


import static com.w67clement.mineapi.enums.MinecraftVersion.v1_8_R1;
import static com.w67clement.mineapi.enums.MinecraftVersion.v1_9_R1;

public enum PacketList
{

    ALL("ALL", "ALL", null),
    // Packet play out
    PacketPlayOutAbilities("PacketPlayOutAbilities", "PlayerAbilitiesMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutAnimation("PacketPlayOutAnimation", "AnimateEntityMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutAttachEntity("PacketPlayOutAttachEntity", "AttachEntityMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutBed("PacketPlayOutBed", "", PacketType.PACKETPLAYOUT),
    PacketPlayOutBlockAction("PacketPlayOutBlockAction", "BlockActionMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutBlockBreakAnimation("PacketPlayOutBlockBreakAnimation", "", PacketType.PACKETPLAYOUT, PacketBlockBreakAnimation.class),
    PacketPlayOutBlockChange("PacketPlayOutBlockChange", "BlockChangeMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutBoss("PacketPlayOutBoss", "", PacketType.PACKETPLAYOUT, v1_9_R1),
    PacketPlayOutCamera("PacketPlayOutCamera", "CameraMessage", PacketType.PACKETPLAYOUT, v1_8_R1),
    PacketPlayOutChat("PacketPlayOutChat", "ChatMessage", PacketType.PACKETPLAYOUT, PacketChat.class),
    PacketPlayOutCloseWindow("PacketPlayOutCloseWindow", "CloseWindowMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutCollect("PacketPlayOutCollect", "CollectItemMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutCombatEvent("PacketPlayOutCombatEvent", "CombatEventMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutCustomPayload("PacketPlayOutCustomPayload", "PluginMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutCustomSoundEffect("PacketPlayOutCustomSoundEffect", "", PacketType.PACKETPLAYOUT, v1_9_R1),
    PacketPlayOutEntity("PacketPlayOutEntity", "", PacketType.PACKETPLAYOUT),
    PacketPlayOutEntityDestroy("PacketPlayOutEntityDestroy", "DestroyEntitiesMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutEntityEffect("PacketPlayOutEntityEffect", "EntityEffectMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutEntityEquipment("PacketPlayOutEntityEquipment", "EntityEquipmentMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutEntityHeadRotation("PacketPlayOutEntityHeadRotation", "EntityHeadRotationMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutEntityLook("PacketPlayOutEntityLook", "", PacketType.PACKETPLAYOUT),
    PacketPlayOutEntityMetadata("PacketPlayOutEntityMetadata", "EntityMetadataMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutEntityStatus("PacketPlayOutEntityStatus", "EntityStatusMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutEntityTeleport("PacketPlayOutEntityTeleport", "EntityTeleportMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutEntityVelocity("PacketPlayOutEntityVelocity", "EntityVelocityMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutExperience("PacketPlayOutExperience", "ExperienceMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutExplosion("PacketPlayOutExplosion", "ExplosionMessage", PacketType.PACKETPLAYOUT, PacketExplosion.class),
    PacketPlayOutGameStateChange("PacketPlayOutGameStateChange", "StateChangeMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutHeldItemSlot("PacketPlayOutHeldItemSlot", "HeldItemMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutKeepAlive("PacketPlayOutKeepAlive", "PingMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutKickDisconnect("PacketPlayOutKickDisconnect", "KickMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutLogin("PacketPlayOutLogin", "JoinGameMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutMap("PacketPlayOutMap", "MapDataMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutMapChunk("PacketPlayOutMapChunk", "ChunkDataMessage", PacketType.PACKETPLAYOUT),
    @Deprecated PacketPlayOutMapChunkBulk("PacketPlayOutMapChunkBulk", "ChunkBulkMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutMount("PacketPlayOutMount", "", PacketType.PACKETPLAYOUT, v1_9_R1),
    PacketPlayOutMultiBlockChange("PacketPlayOutMultiBlockChange", "MultiBlockChangeMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutNamedEntitySpawn("PacketPlayOutNamedEntitySpawn", "SpawnPlayerMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutNamedSoundEffect("PacketPlayOutNamedSoundEffect", "PlaySoundMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutOpenSignEditor("PacketPlayOutOpenSignEditor", "SignEditorMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutOpenWindow("PacketPlayOutOpenWindow", "OpenWindowMessage", PacketType.PACKETPLAYOUT, WindowOpen.class),
    PacketPlayOutPlayerInfo("PacketPlayOutPlayerInfo", "UserListItemMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutPlayerListHeaderFooter("PacketPlayOutPlayerListHeaderFooter", "UserListHeaderFooterMessage", PacketType.PACKETPLAYOUT, MinecraftVersion.v1_8_R1, TabTitle.class),
    PacketPlayOutPosition("PacketPlayOutPosition", "", PacketType.PACKETPLAYOUT),
    PacketPlayOutRelEntityMove("PacketPlayOutRelEntityMove", "RelativeEntityPositionMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutRelEntityMoveLook("PacketPlayOutRelEntityMoveLook", "RelativeEntityPositionRotationMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutRemoveEntityEffect("PacketPlayOutRemoveEntityEffect", "EntityRemoveEffectMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutResourcePackSend("PacketPlayOutResourcePackSend", "ResourcePackSendMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutRespawn("PacketPlayOutRespawn", "RespawnMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutScoreboardDisplayObjective("PacketPlayOutScoreboardDisplayObjective", "", PacketType.PACKETPLAYOUT),
    PacketPlayOutScoreboardObjective("PacketPlayOutScoreboardObjective", "", PacketType.PACKETPLAYOUT),
    PacketPlayOutScoreboardScore("PacketPlayOutScoreboardScore", "", PacketType.PACKETPLAYOUT),
    PacketPlayOutScoreboardTeam("PacketPlayOutScoreboardTeam", "", PacketType.PACKETPLAYOUT),
    PacketPlayOutServerDifficulty("PacketPlayOutServerDifficulty", "ServerDifficultyMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutSetCompression("PacketPlayOutSetCompression", "SetCompressionMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutSetCooldown("PacketPlayOutSetCooldown", "", PacketType.PACKETPLAYOUT, v1_9_R1),
    PacketPlayOutSetSlot("PacketPlayOutSetSlot", "SetWindowSlotMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutSpawnEntity("PacketPlayOutSpawnEntity", "SpawnObjectMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutSpawnEntityExperienceOrb("PacketPlayOutSpawnEntityExperienceOrb", "SpawnXpOrbMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutSpawnEntityLiving("PacketPlayOutSpawnEntityLiving", "SpawnMobMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutSpawnEntityPainting("PacketPlayOutSpawnEntityPainting", "SpawnPaintingMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutSpawnEntityWeather("PacketPlayOutSpawnEntityWeather", "SpawnLightningStrikeMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutSpawnPosition("PacketPlayOutSpawnPosition", "SpawnPositionMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutStatistic("PacketPlayOutStatistic", "StatisticMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutTabComplete("PacketPlayOutTabComplete", "TabCompleteResponseMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutTileEntityData("PacketPlayOutTileEntityData", "UpdateBlockEntityMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutTitle("PacketPlayoutTitle", "TitleMessage", PacketType.PACKETPLAYOUT, MinecraftVersion.v1_8_R1),
    PacketPlayOutTransaction("PacketPlayOutTransaction", "TransactionMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutUnloadChunk("PacketPlayOutUnloadChunk", "", PacketType.PACKETPLAYOUT, v1_9_R1),
    PacketPlayOutUpdateAttributes("PacketPlayOutUpdateAttributes", "", PacketType.PACKETPLAYOUT),
    PacketPlayOutUpdateEntityNBT("PacketPlayOutUpdateEntityNBT", "UpdateEntityNBTMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutUpdateHealth("PacketPlayOutUpdateHealth", "HealthMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutUpdateSign("PacketPlayOutUpdateSign", "UpdateSignMessage", PacketType.PACKETPLAYOUT, PacketUpdateSign.class),
    PacketPlayOutUpdateTime("PacketPlayOutUpdateTime", "TimeMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutVehicleMove("PacketPlayOutVehicleMove", "", PacketType.PACKETPLAYOUT, v1_9_R1),
    PacketPlayOutWindowData("PacketPlayOutWindowData", "WindowPropertyMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutWindowItems("PacketPlayOutWindowItems", "SetWindowContentsMessage", PacketType.PACKETPLAYOUT, WindowItems.class),
    PacketPlayOutWorldBorder("PacketPlayOutWorldBorder", "WorldBorderMessage", PacketType.PACKETPLAYOUT, v1_8_R1),
    PacketPlayOutWorldEvent("PacketPlayOutWorldEvent", "PlayEffectMessage", PacketType.PACKETPLAYOUT),
    PacketPlayOutWorldParticles("PacketPlayOutWorldParticles", "PlayParticleMessage", PacketType.PACKETPLAYOUT),
    // Packet play in
    PacketPlayInAbilities("PacketPlayInAbilities", "PlayerAbilitiesMessage", PacketType.PACKETPLAYIN),
    PacketPlayInArmAnimation("PacketPlayInArmAnimation", "PlayerSwingArmMessage", PacketType.PACKETPLAYIN),
    PacketPlayInBlockDig("PacketPlayInBlockDig", "DiggingMessage", PacketType.PACKETPLAYIN),
    PacketPlayInBlockPlace("PacketPlayInBlockPlace", "BlockPlacementMessage", PacketType.PACKETPLAYIN),
    PacketPlayInBoatMove("PacketPlayInBoatMove", "", PacketType.PACKETPLAYIN, v1_9_R1),
    PacketPlayInChat("PacketPlayInChat", "IncomingChatMessage", PacketType.PACKETPLAYIN, com.w67clement.mineapi.packets.play.in.PacketPlayInChat.class),
    PacketPlayInClientCommand("PacketPlayInClientCommand", "ClientStatusMessage", PacketType.PACKETPLAYIN, ClientCommand.class),
    PacketPlayInCloseWindow("PacketPlayInCloseWindow", "CloseWindowMessage", PacketType.PACKETPLAYIN),
    PacketPlayInCustomPayload("PacketPlayInCustomPayload", "PluginMessage", PacketType.PACKETPLAYIN),
    PacketPlayInEnchantItem("PacketPlayInEnchantItem", "EnchantItemMessage", PacketType.PACKETPLAYIN),
    PacketPlayInEntityAction("PacketPlayInEntityAction", "PlayerActionMessage", PacketType.PACKETPLAYIN),
    PacketPlayInFlying("PacketPlayInFlying", "", PacketType.PACKETPLAYIN),
    PacketPlayInHeldItemSlot("PacketPlayInHeldItemSlot", "HeldItemMessage", PacketType.PACKETPLAYIN),
    PacketPlayInKeepAlive("PacketPlayInKeepAlive", "PingMessage", PacketType.PACKETPLAYIN),
    PacketPlayInLook("PacketPlayInLook", "PlayerLookMessage", PacketType.PACKETPLAYIN),
    PacketPlayInPosition("PacketPlayInPosition", "PlayerPositionMessage", PacketType.PACKETPLAYIN),
    PacketPlayInPositionLook("PacketPlayInPositionLook", "PlayerPositionLookMessage", PacketType.PACKETPLAYIN),
    PacketPlayInResourcePackStatus("PacketPlayInResourcePackStatus", "ResourcePackStatusMessage", PacketType.PACKETPLAYIN),
    PacketPlayInSetCreativeSlot("PacketPlayInSetCreativeSlot", "CreativeItemMessage", PacketType.PACKETPLAYIN),
    PacketPlayInSettings("PacketPlayInSettings", "ClientSettingsMessage", PacketType.PACKETPLAYIN),
    PacketPlayInSpectate("PacketPlayInSpectate", "SpectateMessage", PacketType.PACKETPLAYIN),
    PacketPlayInSteerVehicle("PacketPlayInSteerVehicle", "SteerVehicleMessage", PacketType.PACKETPLAYIN),
    PacketPlayInTabComplete("PacketPlayInTabComplete", "TabCompleteMessage", PacketType.PACKETPLAYIN),
    PacketPlayInTeleportAccept("PacketPlayInTeleportAccept", "", PacketType.PACKETPLAYIN, v1_9_R1),
    PacketPlayInTransaction("PacketPlayInTransaction", "TransactionMessage", PacketType.PACKETPLAYIN),
    PacketPlayInUpdateSign("PacketPlayInUpdateSign", "UpdateSignMessage", PacketType.PACKETPLAYIN),
    PacketPlayInUseEntity("PacketPlayInUseEntity", "InteractEntityMessage", PacketType.PACKETPLAYIN),
    PacketPlayInUseItem("PacketPlayInUseItem", "", PacketType.PACKETPLAYIN, v1_9_R1),
    PacketPlayInVehicleMove("PacketPlayInVehicleMove", "", PacketType.PACKETPLAYIN, v1_9_R1),
    PacketPlayInWindowClick("PacketPlayInWindowClick", "WindowClickMessage", PacketType.PACKETPLAYIN),

    // Status

    PacketStatusInPing("PacketStatusInPing", "StatusPingMessage", PacketType.PACKETSTATUS),
    PacketStatusInStart("PacketStatusInStart", "StatusRequestMessage", PacketType.PACKETSTATUS),

    PacketStatusOutPong("PacketStatusOutPong", "StatusPingMessage", PacketType.PACKETSTATUS, com.w67clement.mineapi.packets.status.PacketStatusOutPong.class),
    PacketStatusOutServerInfo("PacketStatusOutServerInfo", "StatusResponseMessage", PacketType.PACKETSTATUS, com.w67clement.mineapi.packets.status.PacketStatusOutServerInfo.class),

    // Handshake

    Handshake("PacketHandshakingInSetProtocol", "HandshakeMessage", PacketType.HANDSHAKE, PacketHandshake.class);

    private String packetName;
    private String packetAliases;
    private PacketType packetType;
    private MinecraftVersion packetVersion;
    private Class<? extends NmsPacket> mineapiPacket;

    PacketList(String packetName, String packetAliases, PacketType packetType)
    {
        this.packetName = packetName;
        this.packetAliases = packetAliases;
        this.packetType = packetType;
        this.packetVersion = MinecraftVersion.UNKNOWN;
    }

    PacketList(String packetName, String packetAliases, PacketType packetType, Class<? extends NmsPacket> mineapi_packet)
    {
        this.packetName = packetName;
        this.packetAliases = packetAliases;
        this.packetType = packetType;
        this.packetVersion = MinecraftVersion.UNKNOWN;
        this.mineapiPacket = mineapi_packet;
    }

    PacketList(String packetName, String packetAliases, PacketType packetType, MinecraftVersion packetVersion)
    {
        this.packetName = packetName;
        this.packetAliases = packetAliases;
        this.packetType = packetType;
        this.packetVersion = packetVersion;
    }

    PacketList(String packetName, String packetAliases, PacketType packetType, MinecraftVersion packetVersion, Class<? extends NmsPacket> mineapi_packet)
    {
        this.packetName = packetName;
        this.packetAliases = packetAliases;
        this.packetType = packetType;
        this.packetVersion = packetVersion;
        this.mineapiPacket = mineapi_packet;
    }

    public static PacketList getPacketByName(String name)
    {
        for (PacketList packet : values())
        {
            if (packet.getPacketName().equals(name))
            {
                return packet;
            }
        }
        return null;
    }

    public static PacketList getPacketByAliase(String name)
    {
        for (PacketList packet : values())
        {
            if (packet.getPacketAliases().contains(name))
            {
                return packet;
            }
        }
        return null;
    }

    public String getPacketName()
    {
        return this.packetName;
    }

    public String getPacketAliases()
    {
        return this.packetAliases;
    }

    public PacketType getPacketType()
    {
        return this.packetType;
    }

    public MinecraftVersion getPacketVersion()
    {
        return this.packetVersion;
    }

    public Class<? extends NmsPacket> getMineAPIPacket()
    {
        return this.mineapiPacket;
    }

    public boolean hasMineAPIPacket()
    {
        return this.mineapiPacket != null;
    }
}
