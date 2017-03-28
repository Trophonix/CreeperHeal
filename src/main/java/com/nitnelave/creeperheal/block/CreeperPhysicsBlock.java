package com.nitnelave.creeperheal.block;

import org.bukkit.block.BlockState;

import com.nitnelave.creeperheal.config.CfgVal;
import com.nitnelave.creeperheal.config.CreeperConfig;

/**
 * Implementation of CreeperBlock for block affected by gravity.
 * 
 * @author nitnelave
 * 
 */
class CreeperPhysicsBlock extends CreeperBlock
{

    /*
     * Constructor.
     */
    CreeperPhysicsBlock(BlockState blockState)
    {
        super(blockState);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nitnelave.creeperheal.block.CreeperBlock#update()
     */
    @Override
    public void update()
    {
        if (CreeperConfig.getBool(CfgVal.PREVENT_BLOCK_FALL))
            FallIndex.putFallPrevention(getBlock().getLocation());
        super.update();
    }

}
