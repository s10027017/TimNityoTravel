package com.example.tim.nityo.travel.utils

import android.app.Activity
import com.example.tim.nityo.travel.base.BaseActivity

fun Activity.loading(isShown: Boolean) {
    if (this is BaseActivity<*>) {
        loading(isShown)
    }
}

fun Activity.showError(display: BaseActivity.ErrorDisplay) {
    if (this is BaseActivity<*>) {
        showError(display)
    }
}
