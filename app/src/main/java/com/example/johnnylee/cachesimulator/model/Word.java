package com.example.johnnylee.cachesimulator.model;

/**
 * Created by johnnylee on 02/12/16.
 */

public class Word {
    private String adress;
    private String content;

    public Word() {
        content = null;
    }

    public Word(String adress, String content) {
        this.adress = adress;
        this.content = content;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
