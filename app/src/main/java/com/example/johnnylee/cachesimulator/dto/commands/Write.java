package com.example.johnnylee.cachesimulator.dto.commands;

/**
 * Created by johnnylee on 05/12/16.
 */
public class Write {

    private int adress;
    private String value;

    public Write(int address, String value) {
        this.adress = address;
        this.value = value;
    }

    public int getAddress() {
        return adress;
    }

    public String getValue() {
        return value;
    }

}
