package com.fast.common.base

import android.content.res.Configuration
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.fast.common.utils.UnCheckedUtils.cast
import com.fast.common.utils.loge
import com.fast.common.vm.BaseViewModel
import com.fast.res.R
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import java.lang.reflect.ParameterizedType

abstract class XBaseActivity<B : ViewBinding, VM : BaseViewModel> : BaseActivity() {
    private val tag = javaClass.name
    lateinit var mViewBinding: B
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectViewBindingAndViewModel()
        initImmersionBar()
        initViews()
        initEvent()
        loadData()
    }

    private fun initImmersionBar() {
        immersionBar {
            statusBarColor(R.color.purple_700)
            navigationBarColor(R.color.purple_700)
        }
    }

    /**
     * 注册ViewBinding和ViewModel
     */
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        ImmersionBar.with(this).init()
    }

//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

    //    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                v?.let {
                    if (isShouldHideKeyboard(v, ev)) {
                        v.requestFocus()
                        hideKeyboard(v.windowToken)
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v 当前焦点view
     * @param event 事件对象
     * @return true-需要隐藏键盘
     */
    private fun isShouldHideKeyboard(v: View, event: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return (event.x <= left || event.x >= right
                    || event.y <= top || event.y >= bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token token
     */
    protected fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val im = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    open fun loadData() {

    }

    open fun initViews() {

    }

    open fun initEvent() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}