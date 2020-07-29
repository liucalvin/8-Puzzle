package com.example.a8_puzzle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TilesAdapter extends BaseAdapter {
    
    private static final String TAG = "TilesAdapter";
    private final List<Tile> tiles;
    private final Context context;
    
    public TilesAdapter(List<Tile> tiles, Context context) {
        this.tiles = tiles;
        this.context = context;
    }
    
    @Override
    public int getCount() {
        if (tiles == null) return 0;
        return tiles.size();
    }
    
    @Override
    public Object getItem(int position) {
        return tiles.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get tile
        final Tile tile = tiles.get(position);
        
        // instantiate a new cell view using the tile layout file
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.tile_layout, null);
        }
        // set the text to the number of the tile
        final TextView tileNumber = convertView.findViewById(R.id.tile_layout_number);
        tileNumber.setText(String.valueOf(tile.getNumber()));
        
        // make the blank tile invisible
        if (tile.getNumber() == 9) {
            tileNumber.setVisibility(View.INVISIBLE);
            tileNumber.setClickable(false);
        }
        return convertView;
    }
}
