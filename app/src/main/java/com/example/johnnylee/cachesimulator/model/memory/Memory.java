package com.example.johnnylee.cachesimulator.model.memory;

import com.example.johnnylee.cachesimulator.model.Block;

/**
 * Created by johnnylee on 02/12/16.
 */

public abstract class Memory {
    private int blockAmount;

    public Memory(int blockAmount) {
        this.blockAmount = blockAmount;
    }

    public int getBlockAmount() {
        return blockAmount;
    }

    public void setBlockAmount(int blockAmount) {
        this.blockAmount = blockAmount;
    }

}
