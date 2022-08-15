package com.fast.app.viewmodel

import com.fast.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LineViewModel : BaseViewModel() {
    val clickState = MutableStateFlow(CommonString(""))
}