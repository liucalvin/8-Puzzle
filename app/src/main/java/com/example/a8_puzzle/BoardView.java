package com.example.a8_puzzle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class BoardView extends View {
    
    private Board board;
    private double length, width;
    
    
    public BoardView(Context context, AttributeSet attrs, Board board) {
        super(context);
        this.board = board;
    }
    
    
}
