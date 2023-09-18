package com.example.tim.nityo.travel.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup

object LayoutUtility {

    /**
     * 是否有虛擬navigation bar
     */
    fun hasNavBar(activity: Activity): Boolean {
        val resources = activity.resources
        val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        return id > 0 && resources.getBoolean(id)
    }

    /**
     * 取得navigation bar height
     */
    fun getNavigationBarHeight(activity: Activity): Int {
        val resources: Resources = activity.resources
        val resourceId: Int =
            resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    fun setHeight(view: View, height: Float) {
        val params = view.layoutParams
        params.height = height.toInt()
        view.layoutParams = params
    }

    fun setMarginBottom(view: View, newMargin: Int) {
        try {
            val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(
                layoutParams.leftMargin,
                layoutParams.topMargin,
                layoutParams.rightMargin,
                newMargin
            )
            view.layoutParams = layoutParams

        } catch (ex: Exception) {
        }

    }

    fun getDensity(context: Context): Float {
        val metrics = context.resources.displayMetrics
        return metrics.density
    }


    fun convertDpToPixel(context: Context, dp: Float): Int {
        return (dp * getDensity(context)).toInt()
    }

}
