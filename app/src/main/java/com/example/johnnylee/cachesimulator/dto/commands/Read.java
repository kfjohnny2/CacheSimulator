package com.example.johnnylee.cachesimulator.dto.commands;

/**
 * Created by johnnylee on 05/12/16.
 */
public class Read {
    private int adress;

    public Read(int address) {
        this.adress = address;
    }

    public int getAddress() {
        return adress;
    }
}
