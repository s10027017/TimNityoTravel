package com.example.tim.nityo.travel.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingAdapter<M, VDB : ViewDataBinding> : RecyclerView.Adapter<BaseBindingAdapter.BaseBindingViewHolder>() {

    var mList: ArrayList<M> = arrayListOf()

    open fun addList(list: ArrayList<M>) {
        val startPosition = mList.size
        mList.addAll(list)
        notifyItemRangeInserted(startPosition, mList.size)
    }

    open fun setList(list: ArrayList<M>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    open fun clearList() {
        mList.clear()
        notifyDataSetChanged()
    }

    open fun remove(position: Int) {
        if (mList.size > position) {
            mList.removeAt(position)
        }
    }

    fun getList(): ArrayList<M> {
        return mList
    }

    class BaseBindingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        val binding: VDB = DataBindingUtil.inflate(LayoutInflater.from(parent.context), this.getLayoutResId(viewType), parent, false)
        return BaseBindingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        val binding: VDB? = DataBindingUtil.getBinding(holder.itemView)
        this.onBindItem(binding, this.mList[position], position)
    }

    override fun getItemCount(): Int {
        return this.mList.size
    }

    @LayoutRes
    protected abstract fun getLayoutResId(viewType: Int): Int

    protected abstract fun onBindItem(binding: VDB?, item: M, position: Int)

}