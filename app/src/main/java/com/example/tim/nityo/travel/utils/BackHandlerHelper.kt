package com.example.tim.nityo.travel.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


object BackHandlerHelper {
    /**
     * 將back事件分發給 FragmentManager 中管理的子Fragment，如果該 FragmentManager 中的所有Fragment都
     * 沒有處理back事件，則嘗試 FragmentManager.popBackStack()
     *
     * @return 如果處理了back鍵則返回 **true**
     * @see .handleBackPress
     * @see .handleBackPress
     */
    fun handleBackPress(fragmentManager: FragmentManager): Boolean {
        val fragments: List<Fragment> = fragmentManager.fragments
        for (i in fragments.indices.reversed()) {
            val child: Fragment = fragments[i]
            if (isFragmentBackHandled(child)) {
                return true
            }
        }
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return false
    }

    fun handleBackPress(fragment: Fragment): Boolean {
        return handleBackPress(fragment.childFragmentManager)
    }

    fun handleBackPress(fragmentActivity: FragmentActivity): Boolean {
        return handleBackPress(fragmentActivity.supportFragmentManager)
    }

    /**
     * 判斷Fragment是否處理了Back鍵
     *
     * @return 如果處理了back鍵則返回 **true**
     */
    fun isFragmentBackHandled(fragment: Fragment?): Boolean {
        return (fragment != null && fragment.isVisible
                && fragment.userVisibleHint //for ViewPager
                && fragment is FragmentBackHandler
                && (fragment as FragmentBackHandler).onBackPressed())
    }
}