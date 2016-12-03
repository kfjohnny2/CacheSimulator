package com.example.johnnylee.cachesimulator.model.memory;

import com.example.johnnylee.cachesimulator.model.Block;
import com.example.johnnylee.cachesimulator.model.Line;

/**
 * Created by johnnylee on 03/12/16.
 */

public class Cache extends Memory{
    private Line[] lines;
    private int lineAmount;

    public Cache(int blockAmount, boolean lastWrite, boolean firstWrite, int accessFrequency, int lineAmount) {
        super(blockAmount, lastWrite, firstWrite, accessFrequency);
        this.lineAmount = lineAmount;
        lines = new Line[lineAmount];
        for(int i = 0; i < lineAmount; i++){
            lines[i] = new Line(new Block(blockAmount));
        }
    }

    public Line[] getLines() {
        return lines;
    }

    public void setLines(Line[] lines) {
        this.lines = lines;
    }

    public int getLineAmount() {
        return lineAmount;
    }

    public void setLineAmount(int lineAmount) {
        this.lineAmount = lineAmount;
    }

    public Line getLineAtPosition(int pos){
        Line line;
        if(pos < getBlockAmount() && pos > 0)
            line = this.lines[pos];
        else
            line = null;
        return line;
    }
}
