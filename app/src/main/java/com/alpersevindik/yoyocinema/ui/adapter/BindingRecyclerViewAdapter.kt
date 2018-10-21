package com.alpersevindik.yoyocinema.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.alpersevindik.yoyocinema.BR
import com.alpersevindik.yoyocinema.helper.diffutil.IDiffUtilModel


class BindingRecyclerViewAdapter<T : IDiffUtilModel>(private val resourceId: Int, private var data: List<T>) : RecyclerView.Adapter<RecyclerViewBindingHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IDiffUtilModel>() {
        override fun areItemsTheSame(oldItem: IDiffUtilModel, newItem: IDiffUtilModel): Boolean {
            return oldItem.diffId == newItem.diffId
        }

        override fun areContentsTheSame(oldItem: IDiffUtilModel, newItem: IDiffUtilModel): Boolean {
            return oldItem == newItem
        }
    }

    private var diffHelper = AsyncListDiffer(AdapterListUpdateCallback(this), AsyncDifferConfig.Builder(DIFF_CALLBACK).build())
    private var clickListener: OnItemClickListener<T>? = null

    init {
        diffHelper.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewBindingHolder {
        val view = LayoutInflater.from(parent.context).inflate(resourceId, parent, false)
        return RecyclerViewBindingHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewBindingHolder, position: Int) {
        holder.binding?.setVariable(BR.item, diffHelper.currentList[holder.adapterPosition])
        clickListener?.let { listener ->
            holder.binding?.root?.setOnClickListener {
                listener.onClick(diffHelper.currentList[holder.adapterPosition] as T, holder.adapterPosition)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnItemClickListener<T>) {
        this.clickListener = onClickListener
    }

    interface OnItemClickListener<T> {
        fun onClick(item: T, position: Int)
    }


    override fun getItemCount(): Int {
        return diffHelper.currentList.size
    }

    fun submitData(data: List<T>) {
        diffHelper.submitList(data)
    }

    override fun getItemId(position: Int): Long {
        return diffHelper.currentList[position].diffId.toLong()
    }

}

