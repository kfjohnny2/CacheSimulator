package com.example.johnnylee.cachesimulator.model.memory;

import com.example.johnnylee.cachesimulator.model.Block;

/**
 * Created by johnnylee on 03/12/16.
 */

public class Main extends Memory{
    private Block[] blocks;

    public Main(int blockAmount, boolean lastWrite, boolean firstWrite, int accessFrequency) {
        super(blockAmount, lastWrite, firstWrite, accessFrequency);
        this.blocks = new Block[blockAmount];
    }

    public Block getBlockAtPosition(int pos){
        Block block;
        if(pos < getBlockAmount() && pos > 0)
            block = this.blocks[pos];
        else
            block = null;
        return block;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public void setBlocks(Block[] blocks) {
        this.blocks = blocks;
    }
}
