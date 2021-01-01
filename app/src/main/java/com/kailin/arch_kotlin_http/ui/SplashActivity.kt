package com.kailin.arch_kotlin_http.ui

import android.view.View
import androidx.activity.viewModels
import com.kailin.arch_kotlin_http.app.BaseActivity
import com.kailin.coroutines_arch.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun initBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun initView() {
    }

    fun onClick(view: View) {
        MainActivity.start(this)
    }
}