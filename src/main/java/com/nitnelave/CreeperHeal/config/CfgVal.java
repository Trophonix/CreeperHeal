package com.nitnelave.CreeperHeal.config;

public enum CfgVal implements CfgValEnumMember {

    BLOCK_PER_BLOCK_INTERVAL("block-per-block.interval", 20, false),
    WAIT_BEFORE_HEAL("wait-before-heal.explosions", 60, false),
    BLOCK_PER_BLOCK("block-per-block.enabled", true, false),
    WAIT_BEFORE_HEAL_BURNT("wait-before-heal.fire", 45, false),
    CRACK_DESTROYED_BRICKS("crack-destroyed-bricks", false, false),
    REPLACE_PROTECTED_CHESTS("replace-protected-chests-immediately", false, false),
    LOG_LEVEL("verbose-level", 1, true),
    DROP_REPLACED_BLOCKS("replacement-conflict.drop-overwritten-blocks", true, true),
    TELEPORT_ON_SUFFOCATE("teleport-when-buried", true, true),
    DROP_DESTROYED_BLOCKS("drop-destroyed-blocks.enabled", true, true),
    DROP_CHANCE("drop-destroyed-blocks.chance", 100, true),
    OVERWRITE_BLOCKS("replacement-conflict.overwrite", true, true),
    PREVENT_BLOCK_FALL("prevent-block-fall", true, true),
    DISTANCE_NEAR("distance-near", 20, true),
    LIGHTWEIHGTMODE("lightweight-mode", false, true),
    ALIAS("command-alias", "ch", true),
    LOG_WARNINGS("log-warnings", true, true),
    PREVENT_CHAIN_REACTION("prevent-chain-reaction", false, true),
    EXPLODE_OBSIDIAN("obsidian.explode", false, true),
    OBSIDIAN_RADIUS("obsidian.radius", 5, true),
    OBSIDIAN_CHANCE("obsidian.chance", 20, true),
    DEBUG("debug-messages", false, true),
    WAIT_BEFORE_BURN_AGAIN("wait-before-burn-again", 240, true);

    private final String key;
    private final Object defaultValue;
    private final boolean advanced;

    private CfgVal (String key, Object value, boolean adv) {
        this.key = key;
        defaultValue = value;
        advanced = adv;
    }

    @Override
    public String getKey () {
        return key;
    }

    @Override
    public Object getDefaultValue () {
        return defaultValue;
    }

    public boolean isAdvanced () {
        return advanced;
    }
}