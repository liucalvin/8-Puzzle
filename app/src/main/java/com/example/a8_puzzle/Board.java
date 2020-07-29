package com.example.a8_puzzle;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Board {
    
    private final String TAG = "Board.java";
    private int numOfMoves;
    private final List<Position> positionList;
    private MoveListener moveListener;
    private final int LENGTH = 3;
    
    public Board() {
        numOfMoves = 0;
        positionList = new ArrayList<>(LENGTH * LENGTH);
        for (int i = 0; i < LENGTH * LENGTH - 1; i++) {
            positionList.add(i, new Position((int) i / 3, i % 3));
            Log.d(TAG, new Position((int) i / 3, i % 3, i + 1).toString());
        }
    }
    
    public interface MoveListener {
        void tileMoved(Position start, Position end, int numOfMoves);
        
        void solved(int numOfMoves);
    }
    
}
