package com.example.johnnylee.cachesimulator.model;

/**
 * Created by johnnylee on 02/12/16.
 */

public class Word {
    private String address;
    private String content;

    public Word() {
        content = "";
    }

    public Word(String address, String content) {
        this.address = address;
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
