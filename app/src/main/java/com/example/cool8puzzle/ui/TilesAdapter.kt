package com.example.cool8puzzle.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.cool8puzzle.R

class TilesAdapter(
    private val context: Context
) : BaseAdapter() {

    private var tiles: List<Int> = listOf()

    fun submitList(list: List<Int>) {
        tiles = list
        notifyDataSetChanged()
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
        var view = convertView
        val tile = tiles[position]

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_tile, parent, false)
        }

        // set the text to the number of the tile
        val tileNumber = view!!.findViewById<TextView>(R.id.number)
        tileNumber.text = tile.toString()

        return view.apply {
            setBlankTile(tile == 9 || tile == 0)
        }
    }

    private fun View.setBlankTile(isBlank: Boolean) {
        isEnabled = !isBlank
        visibility = if (isBlank) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }
}
