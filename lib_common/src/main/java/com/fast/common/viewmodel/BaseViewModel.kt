package com.fast.common.viewmodel

import androidx.lifecycle.ViewModel
import com.fast.common.base.BaseApplication
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel() {
    val appTheme = MutableStateFlow(BaseApplication.currentTheme)
    
    data class CommonString(var string: String)
    data class CommonBoolean(var boolean: Boolean)
}