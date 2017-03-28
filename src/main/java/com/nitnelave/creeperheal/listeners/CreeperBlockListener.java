package com.nitnelave.creeperheal.listeners;

import com.nitnelave.creeperheal.block.BurntBlockManager;
import com.nitnelave.creeperheal.config.CreeperConfig;
import com.nitnelave.creeperheal.config.WCfgVal;
import com.nitnelave.creeperheal.config.WorldConfig;
import com.nitnelave.creeperheal.utils.FactionHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

/**
 * Listener for all block-related events.
 * 
 * @author nitnelave
 * 
 */
public class CreeperBlockListener implements Listener
{

    /**
     * Listener for the BlockBurntEvent. If appropriate, the block is recorded
     * for future replacement.
     * 
     * @param event
     *            The BlockBurntEvent.
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent event)
    {
        WorldConfig world = CreeperConfig.getWorld(event.getBlock().getLocation().getWorld());

        if (world.getBool(WCfgVal.FIRE) && !world.isProtected(event.getBlock())
            && !FactionHandler.shouldIgnore(event.getBlock(), world))
        {
            if (BurntBlockManager.wasRecentlyBurnt(event.getBlock()))
            {
                event.setCancelled(true);
                return;
            }
            BurntBlockManager.recordBurntBlock(event.getBlock());
        }

    }

}
