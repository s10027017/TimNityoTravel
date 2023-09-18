package com.example.tim.nityo.travel.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.tim.nityo.travel.R
import com.example.tim.nityo.travel.utils.LayoutUtility

abstract class BaseActivity<VDB : ViewDataBinding> : AppCompatActivity() {

    private lateinit var mWindow: Window
    protected lateinit var mBinding: VDB

    private lateinit var contentView: ViewGroup
    private lateinit var loadingView: View

    private var isErrorShowing = false

    private val isLoadingShowing get() = contentView.contains(loadingView)

    private val alertError = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        isErrorShowing = false
    }

    private val errorToast by lazy {
        Toast.makeText(this, R.string.popup_network_error_title, Toast.LENGTH_SHORT)
    }

    /**
     * 指定layout
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun thisActivity(): AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mWindow = window
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.root.isFocusable = true
        mBinding.root.isFocusableInTouchMode = true

        contentView = findViewById(android.R.id.content)
        loadingView = layoutInflater.inflate(R.layout.layout_loading, contentView, false)

    }

    fun setStatusBarLight(isLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            if (isLight) {
                window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                window.insetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
            // 針對android 11會將layout延展到虛擬navigation bar，判斷有無虛擬navigation bar，若有再取得navigation bar高度並margin bottom
            if (LayoutUtility.hasNavBar(thisActivity())) {
                LayoutUtility.setMarginBottom(
                    mBinding.root, (LayoutUtility.getNavigationBarHeight(thisActivity()))
                )
            }
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (isLight) {
                window.decorView.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 全螢幕顯示，status bar 不隱藏，activity 上方 layout 會被 status bar 覆蓋。
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE // 配合其他 flag 使用，防止 system bar 改變後 layout 的變動。
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 全螢幕顯示，status bar 不隱藏，activity 上方 layout 會被 status bar 覆蓋。
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS) // 跟系統表示要渲染 system bar 背景。
        }
        window.statusBarColor = Color.TRANSPARENT
    }

    fun loading(isShown: Boolean) {
        if (isShown) {
            if (!isLoadingShowing) {
                contentView.addView(loadingView)
            }
        } else {
            contentView.removeView(loadingView)
        }
    }

    fun showError(display: ErrorDisplay) {
        when (display) {
            ErrorDisplay.Dialog -> {
                if (!isErrorShowing) {
                    isErrorShowing = true


                }
            }
            ErrorDisplay.Toast -> {
                if (errorToast.view?.isShown == false) {
                    errorToast.show()
                }
            }
        }
    }

    sealed class ErrorDisplay {
        object Dialog: ErrorDisplay()
        object Toast: ErrorDisplay()
    }

}

