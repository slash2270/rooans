package com.example.rooans.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.rooans.BR

class ItemViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bindItem(obj: Any?) {
        if (obj != null) {
            viewDataBinding.setVariable(BR.item, obj)
            viewDataBinding.executePendingBindings()
        }
    }

}