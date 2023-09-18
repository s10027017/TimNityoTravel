package com.example.tim.nityo.travel.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.addCallback
import com.example.tim.nityo.travel.R
import com.example.tim.nityo.travel.base.BaseDialogFragment
import com.example.tim.nityo.travel.databinding.DialogFragmentWebviewBinding

class WebviewDialogFragment(private val url : String) : BaseDialogFragment<DialogFragmentWebviewBinding>(){

    override fun getLayoutId(): Int {
        return R.layout.dialog_fragment_webview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 自定義STYLE 解決DialogFragment頂部留白問題
        setStyle(STYLE_NORMAL, R.style.MyCustomTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = this

        // 在onStart部分手機會閃 onCreate無作用
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)

        Handler(Looper.getMainLooper()).postDelayed({
            dialog?.window?.setBackgroundDrawableResource(R.color.black_cc404040)
        }, 1000)

        initWebview()
        handleViewActions()

        //攔截返回事件
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            dismiss()
        }

    }

    private fun initWebview(){
        val client = WebViewClient()
        mBinding.wvPrivacyPolicy.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mBinding.wvPrivacyPolicy.webViewClient = client
        mBinding.wvPrivacyPolicy.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        mBinding.wvPrivacyPolicy.settings.useWideViewPort = true // 支持 Viewport
        mBinding.wvPrivacyPolicy.settings.loadWithOverviewMode = true // 自適應螢幕
        mBinding.wvPrivacyPolicy.settings.builtInZoomControls = true // 是否開啟手指縮放
        mBinding.wvPrivacyPolicy.settings.setSupportZoom(true)  // 是否顯示縮放按鈕(內部有縮放+/-按鈕)
        mBinding.wvPrivacyPolicy.settings.javaScriptEnabled = true
        mBinding.wvPrivacyPolicy.loadUrl(url)
    }

    private fun handleViewActions(){
        mBinding.imgClose.setOnClickListener {
            dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
            Handler(Looper.getMainLooper()).postDelayed({
                dismiss()
            }, 200)
        }
    }

}