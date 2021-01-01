package com.kailin.arch_kotlin_http.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kailin.arch_kotlin_http.utils.HttpHelper

abstract class BaseViewModel : ViewModel() {

    val msgText: MutableLiveData<String> by lazy { MutableLiveData() }
    val loading: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }
    protected val httpHelper: HttpHelper by lazy { HttpHelper() }

    fun onError(e: Throwable) {
        e.printStackTrace()
//        msgText.postValue(e.message)
    }

    override fun onCleared() {
        super.onCleared()
        httpHelper.clear()
    }
}