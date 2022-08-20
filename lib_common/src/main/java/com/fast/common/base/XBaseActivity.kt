package com.fast.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.fast.common.utils.UnCheckedUtils.cast
import com.fast.common.utils.loge
import com.fast.common.vm.BaseViewModel
import java.lang.reflect.ParameterizedType
abstract class XBaseActivity<B : ViewBinding, VM : BaseViewModel> : BaseActivity() {
    private val tag = javaClass.name
    lateinit var mViewBinding: B
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectViewBindingAndViewModel()

        initViews()
        initEvent()
        loadData()
    }

    /**
     * 注册ViewBinding和ViewModel
     */
    @SuppressWarnings("unchecked")
    private fun injectViewBindingAndViewModel() {
        try {
            val type = javaClass.genericSuperclass as ParameterizedType
            var actualTypeArgument = type.actualTypeArguments[0]
            if (actualTypeArgument != null) {
                val cls = actualTypeArgument as Class<*>
                val inflate = cls.getDeclaredMethod("inflate", LayoutInflater::class.java)
                val viewBinding = inflate.invoke(null, layoutInflater)
                mViewBinding = cast(viewBinding)
                setContentView(mViewBinding.root)
            }
            actualTypeArgument = type.actualTypeArguments[1]
            if (actualTypeArgument != null) {
                val cls = cast(actualTypeArgument) as Class<ViewModel>
                val viewModelProvider = ViewModelProvider(this)
                mViewModel = cast(viewModelProvider[cls])
                lifecycle.addObserver(mViewModel)
            }
        } catch (e: Exception) {
            e.loge(tag)
//            LogUtil.e(e)
        }
    }

    abstract fun loadData()

    abstract fun initViews()

    abstract fun initEvent()


}