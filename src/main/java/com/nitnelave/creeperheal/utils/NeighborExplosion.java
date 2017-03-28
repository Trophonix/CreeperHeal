package com.nitnelave.creeperheal.utils;

import com.nitnelave.creeperheal.block.CreeperExplosion;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Implementation of the NeighborFinder for explosions.
 * 
 * @see com.nitnelave.creeperheal.utils.NeighborFinder
 * @see com.nitnelave.creeperheal.block.CreeperExplosion
 * @author nitnelave
 * 
 */
public class NeighborExplosion extends NeighborFinder<CreeperExplosion>
{

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.nitnelave.creeperheal.utils.NeighborFinder#getNeighbor(org.bukkit
     * .Location, java.util.LinkedList)
     */
    @Override
    protected CreeperExplosion getNeighbor(Location loc, ArrayList<CreeperExplosion> list)
    {
        if (list != null)
        {
            World w = loc.getWorld();
            for (CreeperExplosion ex : list)
            {
                Location l = ex.getLocation();
                if (l.getWorld() == w && loc.distance(ex.getLocation()) < ex.getRadius())
                    return ex;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.nitnelave.creeperheal.utils.NeighborFinder#clean()
     */
    @Override
    public void clean()
    {
        Iterator<ArrayList<CreeperExplosion>> iter = map.values().iterator();
        while (iter.hasNext())
        {
            ArrayList<CreeperExplosion> list = iter.next();
            Iterator<CreeperExplosion> it = list.iterator();
            while (it.hasNext())
            {
                CreeperExplosion e = it.next();
                if (e.isEmpty())
                    it.remove();
            }
            if (list.isEmpty())
                iter.remove();
        }
    }

    /**
     * Remove an CreeperExplosion.
     * 
     * @param ex
     *            The explosion to remove.
     */
    public void removeElement(CreeperExplosion ex)
    {
        Location l = ex.getLocation();
        removeElement(ex, l.getX(), l.getZ());
    }

}
