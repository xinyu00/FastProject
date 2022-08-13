package com.cpzero.lib_base.integration

import com.cpzero.lib_base.app.GlobalConfigModule

interface IGlobalConfig {
    fun applyOption(builder: GlobalConfigModule.Builder)
}