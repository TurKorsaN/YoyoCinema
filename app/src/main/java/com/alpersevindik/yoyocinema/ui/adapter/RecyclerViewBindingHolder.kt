package com.alpersevindik.yoyocinema.ui.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewBindingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding: ViewDataBinding? = DataBindingUtil.bind(itemView)

}