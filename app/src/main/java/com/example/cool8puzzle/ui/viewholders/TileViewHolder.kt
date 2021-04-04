package com.example.cool8puzzle.ui.viewholders

import android.view.ViewGroup
import com.example.cool8puzzle.databinding.ItemTileBinding
import com.example.cool8puzzle.entity.Tile

class TileViewHolder(val parent: ViewGroup) :
    BaseViewHolder<Tile, ItemTileBinding>(parent, ItemTileBinding::inflate) {

    companion object {
        fun from(parent: ViewGroup): TileViewHolder {
            return TileViewHolder(parent)
        }
    }

    override fun bind(item: Tile) {
        binding.number.text = item.number.toString()
    }

}
