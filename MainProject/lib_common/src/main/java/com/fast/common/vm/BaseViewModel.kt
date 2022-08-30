package com.fast.common.vm

import androidx.lifecycle.*
import com.fast.common.base.BaseApplication
import com.fast.common.http.RequestCallback
import com.fast.common.http.ResponseError
import com.fast.common.http.ResponseSuccess
import com.fast.common.http.request
import com.fast.common.livedata.NextChangedLiveData
import kotlinx.coroutines.*

open class BaseViewModel() : AndroidViewModel(BaseApplication.instance), DefaultLifecycleObserver {

    // 是否显示loading
    val showLoading = NextChangedLiveData<Boolean>()
    // 是否关闭页面
    val close = NextChangedLiveData<Boolean>()
}

fun <T> BaseViewModel.launch(
    scope: CoroutineScope = this.viewModelScope,
    loading: MutableLiveData<Boolean>? = this.showLoading,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    callbackDispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: suspend CoroutineScope.() -> T,
    success: ((T) -> Unit)? = null,
    error: ((RequestCallbackDelegation<T>, ResponseError) -> Unit)? = ::defaultError
): Job {
    val realCallback = RequestCallbackDelegation(loading, success, error)

    return request(scope, realCallback, dispatcher, callbackDispatcher, block)
}

fun <T> defaultError(callback: RequestCallbackDelegation<T>, throwable: ResponseError) {
    callback.superOnError(throwable)
}

class RequestCallbackDelegation<T>(
    private val loading: MutableLiveData<Boolean>?,
    private val success: ((T) -> Unit)?,
    private val error: ((RequestCallbackDelegation<T>, ResponseError) -> Unit)?
) : RequestCallback<T> {
    init {
        changeLoading(true)
    }

    override fun onSuccess(result: ResponseSuccess<T>) {
        changeLoading(false)
        success?.invoke(result.data as T)
    }

    override fun onError(error: ResponseError) {
        changeLoading(false)
        this.error?.invoke(this, error)
    }

    /**
     * 为了防止在回调success时，发生异常然后回调到onError中重复修改loading状态
     */
    private fun changeLoading(isLoading: Boolean) {
        loading?.apply {
            if (value != isLoading) {
                postValue(isLoading)
            }
        }
    }

    fun superOnError(throwable: ResponseError) {
        if (throwable.t.toString().contains("JobCancellationException"))
            return
        else
            super.onError(throwable)
    }
}