package com.example.johnnylee.cachesimulator.model;

/**
 * Created by johnnylee on 02/12/16.
 */

public class Line {
    private static int id;
    private Block block;

    static{
        id++;
    }

    public Line(Block block) {
        this.block = block;
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

}
