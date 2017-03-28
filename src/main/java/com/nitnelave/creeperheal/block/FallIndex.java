package com.nitnelave.creeperheal.block;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.nitnelave.creeperheal.CreeperHeal;
import com.nitnelave.creeperheal.config.CfgVal;
import com.nitnelave.creeperheal.config.CreeperConfig;
import com.nitnelave.creeperheal.utils.DateLoc;
import com.nitnelave.creeperheal.utils.NeighborDateLoc;

/**
 * Handle the map for the blocks prevented from falling.
 * 
 * @author nitnelave
 * 
 */
public class FallIndex
{

    /*
     * Blocks whose fall should be prevented.
     */
    private static final NeighborDateLoc index = new NeighborDateLoc();

    static
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CreeperHeal.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                cleanUp();
            }
        }, 400, 7200);
    }

    /**
     * Get whether the location is next to a block whose fall is prevented.
     * 
     * @param loc
     *            The location to check.
     * @return Whether the location is next to a block whose fall is prevented.
     */
    public static boolean isNextToFallPrevention(Location loc)
    {
        return CreeperConfig.getBool(CfgVal.PREVENT_BLOCK_FALL) && index.hasNeighbor(loc);
    }

    /**
     * Add the location to the list of blocks that shouldn't fall. The block's
     * fall is prevented until after 200 times the block per block replacement
     * interval.
     * 
     * @param location
     *            The block's location.
     */
    public static void putFallPrevention(Location location)
    {
        if (CreeperConfig.getBool(CfgVal.PREVENT_BLOCK_FALL))
            index.addElement(new DateLoc(new Date(), location), location.getX(), location.getZ());
    }

    /*
     * Clean up by removing the unnecessary blocks from the fall and update
     * indexes.
     */
    private static void cleanUp()
    {
        index.clean();
    }
}
