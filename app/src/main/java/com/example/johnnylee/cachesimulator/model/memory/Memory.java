package com.example.johnnylee.cachesimulator.model.memory;

import com.example.johnnylee.cachesimulator.model.Block;

/**
 * Created by johnnylee on 02/12/16.
 */

public abstract class Memory {
    private int blockAmount;
    private boolean lastWrite;
    private boolean firstWrite;
    private int accessFrequency;

    public Memory(int blockAmount, boolean lastWrite, boolean firstWrite, int accessFrequency) {
        this.blockAmount = blockAmount;
        this.lastWrite = lastWrite;
        this.firstWrite = firstWrite;
        this.accessFrequency = accessFrequency;
    }

    public int getBlockAmount() {
        return blockAmount;
    }

    public void setBlockAmount(int blockAmount) {
        this.blockAmount = blockAmount;
    }

    public boolean isLastWrite() {
        return lastWrite;
    }

    public void setLastWrite(boolean lastWrite) {
        this.lastWrite = lastWrite;
    }

    public boolean isFirstWrite() {
        return firstWrite;
    }

    public void setFirstWrite(boolean firstWrite) {
        this.firstWrite = firstWrite;
    }

    public int getAccessFrequency() {
        return accessFrequency;
    }

    public void setAccessFrequency(int accessFrequency) {
        this.accessFrequency = accessFrequency;
    }
}
