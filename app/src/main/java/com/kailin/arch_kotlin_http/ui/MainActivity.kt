package com.kailin.arch_kotlin_http.ui

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.kailin.arch_kotlin_http.app.BaseActivity
import com.kailin.arch_kotlin_http.repo.typicode.TypiCode
import com.kailin.coroutines_arch.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModels()

    override fun initBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        val adapter = MainAdapter {
            if (it.tag is TypiCode) {
                DetailActivity.start(this, it.tag as TypiCode)
            }
        }
        viewDataBinding?.let { it.recyclerView.adapter = adapter }
        viewModel.typiCodes.observe(this, { adapter.updateData(it) })
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}