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
    private TilesAdapter tilesAdapter;
    private GridView gridView;
    private List<Tile> tiles;
    private TextView stepCounter, solvedText;
    private Button resetButton, scrambleButton;
    private Tile blankTile;
    private View view;
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        
        stepCounter = view.findViewById(R.id.home_step_counter);
        solvedText = view.findViewById(R.id.home_solved_text);
        resetButton = view.findViewById(R.id.home_reset_button);
        scrambleButton = view.findViewById(R.id.home_scramble_button);

        tiles = new ArrayList<>(9);
        scrambleBoard();
        
        gridView = view.findViewById(R.id.home_gridview);
        tilesAdapter = new TilesAdapter(tiles, view.getContext());
        gridView.setAdapter(tilesAdapter);
        
        Log.d(TAG, "Tile added: " + 8 + 1);
        
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
        /*checkIfValid();*/
        resetSteps();
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // check if the move is possible (only distance of 1 to the empty space)
        if (moveIsPossible(position) && boardIsClickable) {
            exchangeWithBlank(position);
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
    
    private void exchangeWithBlank(int position) {
        List<Tile> tempTiles = new ArrayList<>(tiles);
        int blankTileIndex = tempTiles.indexOf(blankTile);
        Tile temp = tempTiles.remove(position);
        tempTiles.add(position, blankTile);
        tempTiles.remove(blankTileIndex);
        tempTiles.add(blankTileIndex, temp);
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
                Log.d(TAG, "Scrambling");
                tilesAdapter.notifyDataSetChanged();
                gridView.setAdapter(tilesAdapter);
                gridView.invalidateViews();
                break;
        }
    }
}