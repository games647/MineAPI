package com.w67clement.mineapi.enums;

public enum ParticleType
{

    BARRIER("BARRIER", "Barrier", MinecraftVersion.v1_8_R1),
    BLOCK_CRACK("BLOCK_CRACK", "Block crack"),
    BLOCK_DUST("BLOCK_DUST", "Block dust"),
    CLOUD("CLOUD", "Cloud"),
    CRIT("CRIT", "Crit"),
    CRIT_MAGIC("CRIT_MAGIC", "Crit magic"),
    DRIP_LAVA("DRIP_LAVA", "Drip lava"),
    DRIP_WATER("DRIP_WATER", "Drip water"),
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", "Enchantment table"),
    EXPLOSION_HUGE("EXPLOSION_HUGE", "Explosion huge"),
    EXPLOSION_LARGE("EXPLOSION_LARGE", "Explosion large"),
    EXPLOSION_NORMAL("EXPLOSION_NORMAL", "Explosion normal"),
    FIREWORKS_SPARK("FIREWORKS_SPARK", "Fireworks spark"),
    FLAME("FLAME", "Flame"),
    FOOTSTEP("FOOTSTEP", "Footstep"),
    HEART("HEART", "heart"),
    ITEM_CRACK("ITEM_CRACK", "Item crack"),
    ITEM_TAKE("ITEM_TAKE", "Item take"),
    LAVA("LAVA", "Lava"),
    MOB_APPEARANCE("MOB_APPEARANCE", "Mob appearance"),
    NOTE("NOTE", "Note"),
    PORTAL("PORTAL", "Portal"),
    REDSTONE("REDSTONE", "Redstone"),
    SLIME("SLIME", "Slime"),
    SMOKE_LARGE("SMOKE_LARGE", "Smoke large"),
    SMOKE_NORMAL("MOKE_NORMAL", "Smoke normal"),
    SNOW_SHOVEL("SNOW_SHOVEL", "Snow shovel"),
    SNOWBALL("SNOWBALL", "Snowball"),
    SPELL("SPELL", "Spell"),
    SPELL_INSTANT("SPELL_INSTANT", "Spell instant"),
    SPELL_MOB("SPELL_MOB", "Spell mob"),
    SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT", "Spell mob ambient"),
    SPELL_WITCH("SPELL_WITCH", "Spell witch"),
    SUSPENDED("SUSPENDED", "Suspended"),
    SUSPENDED_DEPTH("SUSPENDED_DEPTH", "Suspended depth"),
    TOWN_AURA("TOWN_AURA", "Town aura"),
    VILLAGER_ANGRY("VILLAGER_ANGRY", "Villager angry"),
    VILLAGER_HAPPY("VILLAGER_HAPPY", "Villager happy"),
    WATER_BUBBLE("WATER_BUBBLE", "Water bubble"),
    WATER_DROP("WATER_DROP", "Water drop"),
    WATER_SPLASH("WATER_SPLASH", "Water splash"),
    WATER_WAKE("WATER_WAKE", "Water wake");

    private String nmsName;
    private String name;
    private MinecraftVersion particleVersion;

    ParticleType(String nmsName, String name)
    {
        this(nmsName, name, MinecraftVersion.UNKNOW);
    }

    ParticleType(String nmsName, String name, MinecraftVersion version)
    {
        this.nmsName = nmsName;
        this.name = name;
        this.particleVersion = version;
    }

    public String getParticleName()
    {
        return this.name;
    }

    public String getParticleNmsName()
    {
        return this.nmsName;
    }

    public MinecraftVersion getParticleVersion()
    {
        return this.particleVersion;
    }
}
