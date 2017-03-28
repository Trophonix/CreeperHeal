package com.nitnelave.creeperheal.listeners;

import com.nitnelave.creeperheal.block.BurntBlockManager;
import com.nitnelave.creeperheal.block.CreeperBlock;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockIgniteListener implements Listener
{

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onBlockIgnite(BlockIgniteEvent e)
    {
        for (BlockFace b : CreeperBlock.CARDINALS)
            if (BurntBlockManager.wasRecentlyBurnt(e.getBlock().getRelative(b)))
            {
                e.setCancelled(true);
                return;
            }
    }

}
