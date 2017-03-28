package com.nitnelave.creeperheal.block;

import org.bukkit.block.NoteBlock;

/**
 * NoteBlock implementation of CreeperBlock, to hold the note.
 * 
 * @author nitnelave
 * 
 */
class CreeperNoteBlock extends CreeperBlock
{

    /*
     * Constructor.
     */
    CreeperNoteBlock(NoteBlock blockState)
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
        ((NoteBlock) getBlock().getState()).setRawNote(((NoteBlock) blockState).getRawNote());
    }

}
