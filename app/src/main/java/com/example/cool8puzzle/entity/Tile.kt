package com.example.cool8puzzle.entity

import com.example.cool8puzzle.ui.viewholders.AdapterItem
import com.example.cool8puzzle.ui.viewholders.AdapterItemType

data class Tile(val number: Int): AdapterItem(AdapterItemType.TILE)