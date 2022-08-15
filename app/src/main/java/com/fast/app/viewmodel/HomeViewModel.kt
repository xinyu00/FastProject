package com.fast.app.viewmodel

import com.fast.common.base.BaseApplication
import com.fast.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class HomeViewModel : BaseViewModel() {
    val loadApiDataState = MutableSharedFlow<Boolean>()//注意：MutableSharedFlow可以发射相同的值

    fun switchToNextTheme() {
        val nextTheme = appTheme.value.nextTheme()
        BaseApplication.currentTheme = nextTheme
        appTheme.value = nextTheme
    }

    data class HomeDrawerViewState(
        val switchToNextTheme: () -> Unit,
        val updateProfile: (faceUrl: String, nickname: String, signature: String) -> Unit,
        val logout: () -> Unit,
    )
}