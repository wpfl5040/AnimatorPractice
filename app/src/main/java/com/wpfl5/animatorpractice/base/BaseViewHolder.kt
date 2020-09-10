package com.wpfl5.animatorpractice.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<Binding : ViewDataBinding>(val binding: Binding) : RecyclerView.ViewHolder(binding.root)
