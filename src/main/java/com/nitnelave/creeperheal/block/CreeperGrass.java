package com.nitnelave.creeperheal.block;

import org.bukkit.Material;
import org.bukkit.block.BlockState;

import com.nitnelave.creeperheal.config.CreeperConfig;
import com.nitnelave.creeperheal.config.WCfgVal;

class CreeperGrass extends CreeperBlock
{

    CreeperGrass(BlockState blockState)
    {
        super(blockState);
        if (CreeperConfig.getWorld(getWorld()).getBool(WCfgVal.GRASS_TO_DIRT))
            blockState.setType(Material.DIRT);
    }

}
