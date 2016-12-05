package com.example.johnnylee.cachesimulator.model;

import com.example.johnnylee.cachesimulator.dto.Config;

/**
 * Created by johnnylee on 02/12/16.
 */

public class Block {
    private static int id;
    private int blockSize;
    private Word[] words;

    static {
        id++;
    }

    public Block(Config config) {
        this.blockSize = config.getBlockSize();
        this.words = new Word[blockSize];
        for (int i = 0; i < blockSize; i++) {
            words[i] = new Word();
        }
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Block.id = id;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public Word[] getWords() {
        return words;
    }

    public void setWords(Word[] words) {
        this.words = words;
    }

    public void setWordAt(int pos, String value) {
        this.words[pos].setContent(value);
    }

    public Word getWordAtPosition(int pos) {
        return this.words[pos];
    }
}
