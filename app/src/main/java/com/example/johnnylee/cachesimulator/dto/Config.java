package com.example.johnnylee.cachesimulator.dto;

/**
 * Created by johnnylee on 02/12/16.
 */

public class Config {
    private int blockSize;
    private int cacheLineSize;
    private int blockMainMemorySize;
    private int mappingType;
    private int parcialSetSize;
    private int overwriteType;
    private int writingType;

    public Config(int blockSize, int cacheLineSize, int blockMainMemorySize, int mappingType, int parcialSetSize, int overwriteType, int writingType) {
        this.blockSize = blockSize;
        this.cacheLineSize = cacheLineSize;
        this.blockMainMemorySize = blockMainMemorySize;
        this.mappingType = mappingType;
        this.parcialSetSize = parcialSetSize;
        this.overwriteType = overwriteType;
        this.writingType = writingType;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getCacheLineSize() {
        return cacheLineSize;
    }

    public void setCacheLineSize(int cacheLineSize) {
        this.cacheLineSize = cacheLineSize;
    }

    public int getBlockMainMemorySize() {
        return blockMainMemorySize;
    }

    public void setBlockMainMemorySize(int blockMainMemorySize) {
        this.blockMainMemorySize = blockMainMemorySize;
    }

    public int getMappingType() {
        return mappingType;
    }

    public void setMappingType(int mappingType) {
        this.mappingType = mappingType;
    }

    public int getParcialSetSize() {
        return parcialSetSize;
    }

    public void setParcialSetSize(int parcialSetSize) {
        this.parcialSetSize = parcialSetSize;
    }

    public int getOverwriteType() {
        return overwriteType;
    }

    public void setOverwriteType(int overwriteType) {
        this.overwriteType = overwriteType;
    }

    public int getWritingType() {
        return writingType;
    }

    public void setWritingType(int writingType) {
        this.writingType = writingType;
    }
}
