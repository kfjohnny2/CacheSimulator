package com.example.johnnylee.cachesimulator.model;

import com.example.johnnylee.cachesimulator.dto.Config;

/**
 * Created by johnnylee on 02/12/16.
 */

public class Line {
    private int tag;
    private int hits;
    private static int id;
    private Block block;

    static{
        id++;
    }

    public Line(Config config) {
        block = new Block(config);
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Line.id = id;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }
}
