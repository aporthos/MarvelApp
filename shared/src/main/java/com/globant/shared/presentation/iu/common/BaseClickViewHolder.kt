package com.globant.shared.presentation.iu.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.globant.shared.interfaces.OnClickListener

abstract class BaseClickViewHolder<IT, DB : ViewDataBinding>(binding: DB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: IT, binding: DB, click: OnClickListener<IT>)
}