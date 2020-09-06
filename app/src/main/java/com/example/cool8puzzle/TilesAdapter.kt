package com.example.cool8puzzle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TilesAdapter(
    private val tiles: List<Int>,
    private val context: Context
) : BaseAdapter() {

    companion object {
        private const val TAG = "TilesAdapter"
    }

    override fun getCount(): Int {
        return tiles.size
    }

    override fun getItem(position: Int): Any {
        return tiles[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        // get tile
        var temp = convertView
        val tile = tiles[position]

        // instantiate a new cell view using the tile layout file
        // if convertView is null, assign it; otherwise return it
        if (temp == null) {
            val layoutInflater = LayoutInflater.from(context)
            temp = layoutInflater.inflate(R.layout.tile_layout, parent, false)
        }

        // set the text to the number of the tile
        val tileNumber = temp!!.findViewById<TextView>(R.id.tile_layout_number)
        tileNumber.text = tile.toString()

        // make the blank tile invisible
        if (tile == 9 || tile == 0) {
            tileNumber.visibility = View.INVISIBLE
            tileNumber.isClickable = false
        }
        return temp
    }

}