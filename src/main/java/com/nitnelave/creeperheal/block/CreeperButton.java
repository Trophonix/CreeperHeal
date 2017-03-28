package com.nitnelave.creeperheal.block;

import org.bukkit.block.BlockState;

class CreeperButton extends CreeperBlock
{

    CreeperButton(BlockState b)
    {
        super(b);

        b.setRawData((byte) (getRawData() & 7));
    }
}
