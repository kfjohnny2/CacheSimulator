package com.example.johnnylee.cachesimulator.model.memory;

import com.example.johnnylee.cachesimulator.dto.Config;
import com.example.johnnylee.cachesimulator.model.Block;
import com.example.johnnylee.cachesimulator.model.Word;

/**
 * Created by johnnylee on 03/12/16.
 */

public class Main extends Memory {
    private Config config;
    private Block[] blocks;

    public Main(Config config) {
        super(config.getBlockMainMemorySize());
        this.config = config;
        this.blocks = new Block[config.getBlockMainMemorySize()];
    }

    public Block getBlockAtPosition(int pos) {
        Block block;
        if (pos < getBlockAmount() && pos > 0)
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

    public void setBlockAtPosition(int adress, Block block) {
        blocks[adress] = block;
    }

    public void write(int wordAdr, String value) {
        int blockPos = wordAdr / config.getBlockSize();
        int wordPos = wordAdr % config.getBlockSize();

        if (blocks[blockPos] == null) {
            blocks[blockPos] = new Block(config);
        }

        if (blocks[blockPos].getWords() == null) {
            blocks[blockPos].setWords(new Word[config.getBlockSize()]);
        }

        blocks[blockPos].getWords()[wordPos] = new Word(String.valueOf(wordAdr), value);
    }

    public String show() {
        StringBuilder sb = new StringBuilder("\nMain Memory\n");
        sb.append("Memory content: ");

        for (Block b : blocks) {
            if (b == null) {
                sb.append("{} ");
            } else if (b.getWords() == null) {
                sb.append("{}");
            } else {
                sb.append("{");
                for (Word w : b.getWords()) {
                    if (w == null) {
                        sb.append("[]");
                    } else if (w.getContent() == null) {
                        sb.append("[]");
                    } else {
                        sb.append("[" + w.getContent() + "]");
                    }
                }
                sb.append("}");
            }
        }
        return sb.toString();
    }
}
