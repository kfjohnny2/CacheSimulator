package com.example.johnnylee.cachesimulator.model;

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

    public Block(int blockSize) {
        this.blockSize = blockSize;
        this.words = new Word[blockSize];
        for(int i = 0; i < blockSize; i++)
            words[i] = new Word(0, 0);
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

    public void setWordAt(int pos, int value){
        this.words[pos].setContent(value);
    }

    public Word getWordAtPosition(int pos){
        Word word;
        if(pos < blockSize  && pos > 0)
            word = this.words[pos];
        else
            word = null;
        return word;
    }
}
