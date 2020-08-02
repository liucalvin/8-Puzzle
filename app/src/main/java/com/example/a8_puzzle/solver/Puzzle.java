package com.example.a8_puzzle.solver;

import com.example.a8_puzzle.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class Puzzle {
    
    private static final int LENGTH = 3;
    private static final String TAG = "Puzzle.java";
    private final int tiles[][];
    private final List<Tile> tileList;
    private String movement;
    
    public Puzzle(int[][] tiles) {
        this.tiles = copy(tiles);
        tileList = toList(this.tiles);
    }
    
    public List<Tile> getTileList() {
        return tileList;
    }
    
    private List<Tile> toList(int[][] tiles) {
        List<Tile> tileList = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                tileList.add(index, new Tile(tiles[i][j]));
                index++;
            }
        }
        return tileList;
    }
    
    public String getMovement() {
        return movement;
    }
    
    public void setMovement(String movement) {
        this.movement = movement;
    }
    
    // compares this puzzle to the puzzle of the previous node and returns a description of the movement
    public String movement(Puzzle moved) {
        String direction;
        int movedTileNum;
        // find the moved tile
        int[] initalBlankPosition = this.find(0);
        int[] finalBlankPosition = moved.find(0);
        movedTileNum = tiles[finalBlankPosition[0]][finalBlankPosition[1]];
        // 4 cases:
        // compare row; if initial > final, then the blank tile has been moved up,
        // so the tile has been moved down.
        if (initalBlankPosition[0] > finalBlankPosition[0]) {
            direction = "down";
            
        } else if (initalBlankPosition[0] < finalBlankPosition[0]) {
            // blank tile moved down, so tile moved up
            direction = "up";
            
            // else the movement is horizontal
            // blank tile moved left, so tile is moved right
        } else if (initalBlankPosition[1] > finalBlankPosition[1]) {
            direction = "right";
            
            // else must be left
        } else {
            direction = "left";
        }
        return String.format(Locale.CANADA, "Move tile %d %s", movedTileNum, direction);
    }
    
    private static int[][] copy(int[][] arr) {
        int[][] temp = new int[LENGTH][LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            System.arraycopy(arr[i], 0, temp[i], 0, LENGTH);
        }
        return temp;
    }
    
    public int manhattanDistance() {
        int count = 0;
        for (int i = 1; i <= (LENGTH * LENGTH) - 1; i++) {   // from 1 to n^2 - 1 inclusive
            int[] position = mapToArray(i);
            
            int[] correct = find(i);
            if (position[0] != correct[0]) {
                count += Math.abs(position[0] - correct[0]);
            }
            if (position[1] != correct[1]) {
                count += Math.abs(position[1] - correct[1]);
            }
            
        }
        return count;
    }
    
    private int[] mapToArray(int num) {         // maps [1] to [0,0]
        int a = (num - 1) / LENGTH;
        int b = (num - 1) % LENGTH;
        return new int[]{a, b};
    }
    
    private int mapToIndex(int a, int b) {   // maps [0, 0] to 1
        return (LENGTH * a) + b + 1;
    }
    
    // returns the [row, col] position of the 0 (blank) tile
    private int[] find(int num) {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (tiles[i][j] == num) return new int[]{i, j};
            }
        }
        return new int[]{};
    }
    
    // exchanges the elements in places [a1][a2] and [b1][b2]
    private static void exchange(int[][] arr, int a1, int a2, int b1, int b2) {
        
        int temp = arr[a1][a2];
        arr[a1][a2] = arr[b1][b2];
        arr[b1][b2] = temp;
    }
    
    public boolean isSolved() {
        int[][] goal = new int[LENGTH][LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                goal[j][i] = (LENGTH * (j)) + i + 1;
            }
        }
        // for the bottom right index = 0
        goal[LENGTH - 1][LENGTH - 1] = 0;
        return Arrays.deepEquals(tiles, goal);
    }
    
    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Puzzle temp = (Puzzle) y;
        return Arrays.deepEquals(this.tiles, temp.tiles);
    }
    
    private int[] convertTo1DArray(int[][] arr) {
        int[] tempTiles = new int[LENGTH * LENGTH];
        int count = 0;
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                tempTiles[count] = arr[i][j];
                count++;
            }
        }
        return tempTiles;
    }
    
    public boolean isSolvable() {
        // count the inversions: pairs of tiles i, j such that i < j and tiles[i] > tiles[j]
        // for each tile, count how many tiles are greater than it down the list
        int inversions = 0;
        
        int[] tempTiles = convertTo1DArray(tiles);
        
        for (int i = 0; i < LENGTH * LENGTH; i++) {
            int current = tempTiles[i];
            for (int j = i + 1; j < LENGTH * LENGTH; j++) {
                int tileNum = tempTiles[j];
                if (tileNum != 0) {  // only count the tiles, not the blank tile
                    if (current > tileNum) {
                        inversions++;
                    }
                }
            }
        }
        // if there are an even number of inversions, the puzzle is valid
        return inversions % 2 == 0;
    }
    
    // all neighboring puzzle instances (all possible switches with the 0 element / blank tile)
    public Iterable<Puzzle> neighbors() {
        Stack<Puzzle> puzzles = new Stack<>();
        int[] zeroLocation = find(0);
        int a1 = zeroLocation[0];
        int a2 = zeroLocation[1];
        
        // exchange [a1, a2] with its neighbours that exist
        if (zeroLocation[0] != 0) {
            int[][] temp = copy(tiles);
            exchange(temp, a1, a2, a1 - 1, a2);
            puzzles.push(new Puzzle(temp));
        }
        if (zeroLocation[0] != LENGTH - 1) {
            int[][] temp = copy(tiles);
            exchange(temp, a1, a2, a1 + 1, a2);
            puzzles.push(new Puzzle(temp));
        }
        if (zeroLocation[1] != 0) {
            int[][] temp = copy(tiles);
            exchange(temp, a1, a2, a1, a2 - 1);
            puzzles.push(new Puzzle(temp));
        }
        if (zeroLocation[1] != LENGTH - 1) {
            int[][] temp = copy(tiles);
            exchange(temp, a1, a2, a1, a2 + 1);
            puzzles.push(new Puzzle(temp));
        }
        
        return puzzles;
    }
}

