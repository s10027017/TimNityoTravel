package com.example.tim.nityo.travel.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<VDB : ViewDataBinding> : DialogFragment(){

    protected lateinit var mBinding: VDB
    private lateinit var mWindow: Window

    /**
     * 指定layout
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(getLayoutId(), container, false)
        mBinding = DataBindingUtil.bind(rootView)!!

        mWindow = activity?.window!!

        return rootView
    }

}