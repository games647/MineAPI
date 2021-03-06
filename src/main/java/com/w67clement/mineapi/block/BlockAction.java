package com.w67clement.mineapi.block;

import org.bukkit.Material;

public enum BlockAction
{

    /*
     * Note block
     */
    NOTEBLOCK_HARP(0, 0, BlockActionType.NOTE_BLOCK),
    NOTEBLOCK_DOUBLE_BASS(1, 0, BlockActionType.NOTE_BLOCK),
    NOTEBLOCK_SNARE_DRUM(2, 0, BlockActionType.NOTE_BLOCK),
    NOTEBLOCK_CLICKS_STICKS(3, 0, BlockActionType.NOTE_BLOCK),
    NOTEBLOCK_BASS_DRUM(4, 0, BlockActionType.NOTE_BLOCK),
    /*
     * Piston - Push
     */
    PUSH_DOWN(0, 0, BlockActionType.PISTON),
    PUSH_UP(0, 1, BlockActionType.PISTON),
    PUSH_SOUTH(0, 2, BlockActionType.PISTON),
    PUSH_WEST(0, 3, BlockActionType.PISTON),
    PUSH_NORTH(0, 4, BlockActionType.PISTON),
    PUSH_EAST(0, 5, BlockActionType.PISTON),
    // Pull
    PULL_DOWN(1, 0, BlockActionType.PISTON),
    PULL_UP(1, 1, BlockActionType.PISTON),
    PULL_SOUTH(1, 2, BlockActionType.PISTON),
    PULL_WEST(1, 3, BlockActionType.PISTON),
    PULL_NORTH(1, 4, BlockActionType.PISTON),
    PULL_EAST(1, 5, BlockActionType.PISTON),
    /*
     * Chest
     */
    CHEST_CLOSE(1, 0, BlockActionType.CHEST),
    CHEST_OPEN(1, 1, BlockActionType.CHEST);

    private int action;
    private int data;
    private BlockActionType type;

    BlockAction(int action, int data, BlockActionType type)
    {
        this.action = action;
        this.data = data;
        this.type = type;
    }

    public static BlockAction getByActionId(int action) {
        for (BlockAction blockAction : values()) {
            if (blockAction.action == action)
                return blockAction;
        }
        return null;
    }

    public int getAction()
    {
        return this.action;
    }

    public int getData()
    {
        return this.data;
    }

    public BlockActionType getType()
    {
        return this.type;
    }

    public enum BlockActionType
    {
        CHEST(Material.CHEST),
        NOTE_BLOCK(Material.NOTE_BLOCK),
        PISTON(Material.PISTON_BASE);

        private Material material;

        BlockActionType(Material material)
        {
            this.material = material;
        }

        public Material getMaterial()
        {
            return this.material;
        }
    }

}
