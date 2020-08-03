package com.example.a8_puzzle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    
    private static final String TAG = "Home Fragment";
    private static final int LENGTH = 3;
    private TilesAdapter tilesAdapter;
    private GridView gridView;
    private List<Tile> tiles;
    private TextView stepCounter, solvedText;
    private Tile blankTile;
    private int stepCount;
    private boolean boardIsClickable;
    
    public HomeFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
    
        stepCounter = view.findViewById(R.id.home_step_counter);
        solvedText = view.findViewById(R.id.home_solved_text);
        Button resetButton = view.findViewById(R.id.home_reset_button);
        Button scrambleButton = view.findViewById(R.id.home_scramble_button);
    
        tiles = new ArrayList<>(9);
        scrambleBoard();
    
        gridView = view.findViewById(R.id.home_gridview);
        tilesAdapter = new TilesAdapter(tiles, view.getContext());
        gridView.setAdapter(tilesAdapter);
    
        gridView.setOnItemClickListener(this);
        resetButton.setOnClickListener(this);
        scrambleButton.setOnClickListener(this);
        return view;
    }
    
    private void resetBoard() {
        tiles.clear();
        for (int i = 0; i < 8; i++) {
            tiles.add(i, new Tile(i + 1));
        }
        // the blank tile:
        blankTile = new Tile(8 + 1);
        tiles.add(8, blankTile);
        boardIsClickable = true;
        resetSteps();
    }
    
    private void resetSteps() {
        solvedText.setVisibility(View.INVISIBLE);
        stepCount = 0;
        stepCounter.setText(String.format(Locale.CANADA, "Steps: %d", stepCount));
    }
    
    private void scrambleBoard() {
        tiles.clear();
        resetBoard();
        Collections.shuffle(tiles);
        if (!isValidPuzzle()) {
            scrambleBoard();
        }
    }
    
    private boolean isValidPuzzle() {
        int inversions = 0;
        for (int i = 0; i < LENGTH * LENGTH - 1; i++) {
            int frontTile = tiles.get(i).getNumber();
            if (frontTile == 0) {
                break;
            }
            for (int j = i + 1; j < LENGTH * LENGTH; j++) {
                int backTile = tiles.get(j).getNumber();
                if (frontTile > backTile && backTile != 0) {
                    inversions++;
                }
            }
        }
        Log.d(TAG, "Number of Inversions: " + inversions);
        // if even number of inversions, then the puzzle is valid.
        return inversions % 2 == 0;
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // check if the move is possible (only distance of 1 to the empty space)
        if (moveIsPossible(position) && boardIsClickable) {
            // exchange the tile with the blank tile
            int blankTileIndex = tiles.indexOf(blankTile);
            exchangeTiles(position, blankTileIndex);
    
            stepCount++;
            stepCounter.setText(String.format(Locale.CANADA, "Steps: %d", stepCount));
            tilesAdapter.notifyDataSetChanged();
            gridView.setAdapter(tilesAdapter);
            gridView.invalidateViews();
            if (puzzleIsSolved()) {
                boardIsClickable = false;
                solvedText.setVisibility(View.VISIBLE);
            }
        }
    }
    
    private boolean puzzleIsSolved() {
        for (int i = 0; i < 8; i++) {
            if (tiles.get(i).getNumber() != i + 1) {
                return false;
            }
        }
        return true;
    }
    
    private boolean moveIsPossible(int position) {
        // Reminder: position is 0-indexed
        
        int count = 0;
        int[] pos = mapToArray(position);
        
        int[] correct = mapToArray(tiles.indexOf(blankTile));
        if (pos[0] != correct[0]) {
            count += Math.abs(pos[0] - correct[0]);
        }
        if (pos[1] != correct[1]) {
            count += Math.abs(pos[1] - correct[1]);
        }
        return count == 1;
    }
    
    private void exchangeTiles(int position1, int position2) {
        List<Tile> tempTiles = new ArrayList<>(tiles);
        Tile temp = tempTiles.remove(position1);
        tempTiles.add(position1, blankTile);
        tempTiles.remove(position2);
        tempTiles.add(position2, temp);
        tiles.clear();
        tiles.addAll(tempTiles);
    }
    
    private int[] mapToArray(int num) {
        int a = (num) / 3;
        int b = (num) % 3;
        return new int[]{a, b};
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_reset_button:
                resetBoard();
                tilesAdapter.notifyDataSetChanged();
                gridView.setAdapter(tilesAdapter);
                gridView.invalidateViews();
                break;
            case R.id.home_scramble_button:
                scrambleBoard();
                tilesAdapter.notifyDataSetChanged();
                gridView.setAdapter(tilesAdapter);
                gridView.invalidateViews();
                break;
        }
    }
}