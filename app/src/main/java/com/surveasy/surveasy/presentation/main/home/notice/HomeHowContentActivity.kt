package com.surveasy.surveasy.presentation.main.home.notice

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.surveasy.surveasy.databinding.ActivityHomeHowContentBinding
import com.surveasy.surveasy.presentation.base.BaseActivity

class HomeHowContentActivity :
    BaseActivity<ActivityHomeHowContentBinding>(ActivityHomeHowContentBinding::inflate) {
    override fun initData() {

    }

    override fun initEventObserver() {

    }

    override fun initView() = with(binding) {
        wvForm.settings.javaScriptEnabled = true

        wvForm.webViewClient = WebViewClient()
        wvForm.webChromeClient = WebChromeClient()

        wvForm.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfOiddg83B3n01iKMVP0OsdahKhhr1ozZXn3KTWLw4mi7MdEw/viewform")

        ivBack.setOnClickListener { finish() }
    }
}