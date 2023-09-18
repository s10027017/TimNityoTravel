package com.example.tim.nityo.travel.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tim.nityo.travel.data.RemoteDataSource
import com.example.tim.nityo.travel.utils.SingleLiveEvent

abstract class BaseViewModel: ViewModel() {

    val showNetworkError = SingleLiveEvent<Unit>()
    val showServerError = SingleLiveEvent<Int>()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var loadingCount = 0

    protected fun judgeErrorTypeAndDisplay(status: Int) {
        when (status) {
            RemoteDataSource.NETWORK_ERROR -> displayNetworkError()
            else -> displayServerError(status)
        }
    }

    protected fun displayNetworkError() {
        showNetworkError.call()
    }

    // TODO error code
    protected fun displayServerError(code: Int) {
        showServerError.value = code
    }

    protected fun loadingStart() {
        if (loadingCount == 0) {
            _isLoading.value = true
        }
        loadingCount += 1
    }

    protected fun loadingDone() {
        loadingCount -= 1
        if (loadingCount == 0) {
            _isLoading.value = false
        }
    }

}