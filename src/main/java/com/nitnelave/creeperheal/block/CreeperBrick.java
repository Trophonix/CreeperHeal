package com.nitnelave.creeperheal.block;

import org.bukkit.block.BlockState;

import com.nitnelave.creeperheal.config.CfgVal;
import com.nitnelave.creeperheal.config.CreeperConfig;

class CreeperBrick extends CreeperBlock
{

    CreeperBrick(BlockState blockState)
    {
        super(blockState);

        if (CreeperConfig.getBool(CfgVal.CRACK_DESTROYED_BRICKS) && getRawData() == (byte) 0)
            blockState.setRawData((byte) 2);
    }

}
