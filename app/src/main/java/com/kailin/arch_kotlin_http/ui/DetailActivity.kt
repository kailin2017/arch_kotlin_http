package com.kailin.arch_kotlin_http.ui

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.kailin.arch_kotlin_http.app.BaseActivity
import com.kailin.arch_kotlin_http.repo.typicode.TypiCode
import com.kailin.coroutines_arch.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<DetailViewModel, ActivityDetailBinding>() {

    override val viewModel: DetailViewModel by viewModels()
    override fun initBinding() = ActivityDetailBinding.inflate(layoutInflater)

    override fun initView() {
        viewDataBinding?.let {
            val data = intent.getSerializableExtra(TypiCode.tag) as TypiCode
            it.data = data
            viewModel.initBitmap(data.url)
            viewModel.bitmap.observe(this, { bitmap ->
                it.image.setImageBitmap(bitmap)
                it.progress.hide()
            })
        }
    }

    companion object {
        fun start(context: Context, data: TypiCode) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(TypiCode.tag, data)
            }
            context.startActivity(intent)
        }
    }
}