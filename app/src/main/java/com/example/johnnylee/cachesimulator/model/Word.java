package com.example.johnnylee.cachesimulator.model;

/**
 * Created by johnnylee on 02/12/16.
 */

public class Word {
    private int adress;
    private int content;

    public Word(int adress, int content) {
        this.adress = adress;
        this.content = content;
    }

    public int getAdress() {
        return adress;
    }

    public void setAdress(int adress) {
        this.adress = adress;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }
}
