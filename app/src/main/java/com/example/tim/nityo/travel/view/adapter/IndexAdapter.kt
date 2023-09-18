package com.example.tim.nityo.travel.view.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.tim.nityo.travel.R
import com.example.tim.nityo.travel.data.model.AttractionsDataItem
import com.example.tim.nityo.travel.databinding.ItemIndexBinding

class IndexAdapter(val clickDo: (data : AttractionsDataItem) -> Unit) :
    BaseBindingAdapter<AttractionsDataItem, ViewDataBinding>() {

    private lateinit var mContext : Context

    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.item_index
    }

    override fun onBindItem(binding: ViewDataBinding?, item: AttractionsDataItem, position: Int) {
        if(binding is ItemIndexBinding){
            binding.run {
                model = item
                if(item.images.isNotEmpty()){
                    Glide.with(mContext)
                        .load(item.images[0].src)
                        .into(binding.ivBg)
                }else{
                    Glide.with(mContext)
                        .load(mContext.getDrawable(R.drawable.baseline_android_24))
                        .into(binding.ivBg)
                }
                binding.root.setOnClickListener {
                    clickDo(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        mContext = parent.context
        return super.onCreateViewHolder(parent, viewType)
    }

}