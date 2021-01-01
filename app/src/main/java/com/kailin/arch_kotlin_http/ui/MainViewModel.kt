package com.kailin.arch_kotlin_http.ui

import androidx.lifecycle.MutableLiveData
import com.kailin.arch_kotlin_http.app.BaseViewModel
import com.kailin.arch_kotlin_http.repo.typicode.TypiCode

class MainViewModel : BaseViewModel() {

    val typiCodes: MutableLiveData<MutableList<TypiCode>> by lazy { MutableLiveData() }

    init {
        httpHelper.callApi(
                url = "https://jsonplaceholder.typicode.com/photos",
                exceptionCB = this@MainViewModel::onError,
                formatMethod = TypiCode::string2TypiCodeList,
                successCB = typiCodes::postValue
            )
    }
}