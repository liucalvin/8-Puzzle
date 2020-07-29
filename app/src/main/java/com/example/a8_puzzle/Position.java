package com.example.a8_puzzle;

public class Position {
    
    private final int row, col;
    private Tile tile;
    
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public Position(int row, int col, int tileNumber) {
        this.row = row;
        this.col = col;
        this.tile = new Tile(tileNumber);
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public Tile getTile() {
        return tile;
    }
    
    public void setTile(Tile tile) {
        this.tile = tile;
    }
    
    public boolean hasTile() {
        return tile != null;
    }
    
    public boolean isSlideable() {
        return hasTile();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nRow: ").append(row);
        sb.append("\nCol: ").append(col);
        sb.append("\nTile: ").append(tile.toString());
        return sb.toString();
    }
    
}
