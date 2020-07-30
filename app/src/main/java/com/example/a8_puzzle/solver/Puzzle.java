package com.example.a8_puzzle.solver;

import java.util.Arrays;
import java.util.Stack;

public class Puzzle {
    
    private static final int LENGTH = 3;
    private final int tiles[][];
    
    public Puzzle(int[][] tiles) {
        this.tiles = copy(tiles);
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
    
    private int[] find(int num) {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (tiles[i][j] == num) return new int[]{i, j};
            }
        }
        return new int[]{};
    }
    
    public Puzzle twin() {
        
        int[][] temp = copy(tiles);
        
        // choose two of (0,1), (0,0), or (1,0), depending on which is zero
        // then exchange the non-zero ones.
        if (tiles[0][0] != 0) {
            if (tiles[0][1] != 0) {
                exchange(temp, 0, 0, 0, 1);
            } else {
                exchange(temp, 0, 0, 1, 0);
            }
        } else {
            exchange(temp, 0, 1, 1, 0);
        }
        return new Puzzle(temp);
    }
    
    // exchanges the elements in places [a1][a2] and [b1][b2]
    private static void exchange(int[][] arr, int a1, int a2, int b1, int b2) {
        
        int temp = arr[a1][a2];
        arr[a1][a2] = arr[b1][b2];
        arr[b1][b2] = temp;
    }
    
    public boolean isGoal() {
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

