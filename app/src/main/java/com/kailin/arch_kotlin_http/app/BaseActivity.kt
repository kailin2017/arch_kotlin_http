package com.kailin.arch_kotlin_http.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.kailin.arch_kotlin_http.widget.DialogHelper

abstract class BaseActivity<M : BaseViewModel, V : ViewDataBinding> : AppCompatActivity() {

    protected var viewDataBinding: V? = null
    protected abstract val viewModel: M
    private val dialogHelper = DialogHelper.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = initBinding()
        setContentView(viewDataBinding?.root)
        with(viewModel) {
            msgText.observe(this@BaseActivity, {
                dialogHelper.msgDialog(this@BaseActivity, msg = it)
            })
            loading.observe(this@BaseActivity, {
                if (it) {
                    progressOn()
                } else {
                    progressOff()
                }
            })
        }
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding = null
    }

    protected abstract fun initBinding(): V

    protected abstract fun initView()

    protected fun progressOn() {}

    protected fun progressOff() {}
}