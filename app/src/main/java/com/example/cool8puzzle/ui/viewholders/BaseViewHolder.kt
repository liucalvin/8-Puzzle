package com.example.cool8puzzle.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T: AdapterItem, V: ViewBinding>(
    parent: ViewGroup,
    bindingLayout: (LayoutInflater, ViewGroup?, Boolean) -> V,
    val binding: V = bindingLayout.invoke(LayoutInflater.from(parent.context), parent, false)
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
}

abstract class AdapterItem(itemType: AdapterItemType)

enum class AdapterItemType {
    TILE
}
