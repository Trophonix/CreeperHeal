package com.nitnelave.creeperheal.block;

import org.bukkit.block.CreatureSpawner;

/**
 * MonsterSpawner implementation of CreeperBlock, to save the type of monster.
 * 
 * @author nitnelave
 * 
 */
class CreeperMonsterSpawner extends CreeperBlock
{

    /*
     * Constructor.
     */
    CreeperMonsterSpawner(CreatureSpawner blockState)
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
        ((CreatureSpawner) getBlock().getState()).setCreatureTypeByName(((CreatureSpawner) blockState).getCreatureTypeName());
    }
}
