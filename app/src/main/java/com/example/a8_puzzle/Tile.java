package com.example.a8_puzzle;

public class Tile {
    
    private final int number;
    
    public Tile(int number) {
        this.number = number;
    }
    
    public int getNumber() {
        return number;
    }
    
    @Override
    public String toString() {
        return "Tile number: " + number;
    }
}
