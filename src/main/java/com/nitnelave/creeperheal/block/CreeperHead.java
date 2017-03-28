package com.nitnelave.creeperheal.block;

import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

/**
 * Skull implementation of CreeperBlock, to store and replace the orientation,
 * the owner, etc...
 * 
 * @author nitnelave
 * 
 */
class CreeperHead extends CreeperBlock
{

    /*
     * Constructor.
     */
    CreeperHead(BlockState blockState)
    {
        super(blockState);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nitnelave.creeperheal.block.CreeperBlock#update(boolean)
     */
    @Override
    public void update()
    {
        super.update();
        Skull skull = (Skull) blockState;
        Skull newSkull = ((Skull) blockState.getBlock().getState());
        newSkull.setRotation(skull.getRotation());
        newSkull.setSkullType(skull.getSkullType());
        if (skull.hasOwner())
            newSkull.setOwningPlayer(skull.getOwningPlayer());
        newSkull.update(true);
    }

}
