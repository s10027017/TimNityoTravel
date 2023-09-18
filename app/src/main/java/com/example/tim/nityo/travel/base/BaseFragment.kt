package com.example.tim.nityo.travel.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tim.nityo.travel.utils.BackHandlerHelper
import com.example.tim.nityo.travel.utils.FragmentBackHandler

abstract class BaseFragment : Fragment(), FragmentBackHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        return BackHandlerHelper.handleBackPress(this)
    }

}