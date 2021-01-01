package com.kailin.arch_kotlin_http.ui

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.kailin.arch_kotlin_http.app.BaseViewModel

class DetailViewModel : BaseViewModel() {

    val bitmap: MutableLiveData<Bitmap> by lazy { MutableLiveData() }

    fun initBitmap(url: String) {
        httpHelper.callImage(url) { bitmap.postValue(it) }
    }
}