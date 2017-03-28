package com.nitnelave.creeperheal.block;

import org.bukkit.block.BlockState;

class CreeperPlate extends CreeperBlock
{

    CreeperPlate(BlockState blockState)
    {
        super(blockState);
        blockState.setRawData((byte) 0);
    }

}
