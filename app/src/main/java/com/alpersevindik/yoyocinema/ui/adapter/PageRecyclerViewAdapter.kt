package com.alpersevindik.yoyocinema.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alpersevindik.yoyocinema.BR
import com.alpersevindik.yoyocinema.helper.diffutil.IDiffUtilModel


class PageRecyclerViewAdapter<T>(private val resourceId: Int) : PagedListAdapter<IDiffUtilModel, RecyclerViewBindingHolder>(DIFF_CALLBACK) {

    private var clickListener: OnItemClickListener<T>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewBindingHolder {
        val view = LayoutInflater.from(parent.context).inflate(resourceId, parent, false)
        return RecyclerViewBindingHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewBindingHolder, position: Int) {
        holder.binding?.setVariable(BR.item, getItem(position))
        clickListener?.let { listener ->
            holder.binding?.root?.setOnClickListener {
                listener.onClick(getItem(holder.adapterPosition) as T, holder.adapterPosition)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnItemClickListener<T>) {
        this.clickListener = onClickListener
    }

    interface OnItemClickListener<T> {
        fun onClick(item: T, position: Int)
    }




    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IDiffUtilModel>() {
            override fun areItemsTheSame(oldItem: IDiffUtilModel, newItem: IDiffUtilModel): Boolean {
                return oldItem.diffId == newItem.diffId
            }

            override fun areContentsTheSame(oldItem: IDiffUtilModel, newItem: IDiffUtilModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}