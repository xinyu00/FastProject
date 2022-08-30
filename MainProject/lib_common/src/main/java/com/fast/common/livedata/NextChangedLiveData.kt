package com.fast.common.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * Observer只会接收到observe到LiveData之后发生的value改变的监听
 */
class NextChangedLiveData<T> : MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    // 通过Hook去除粘性
    private fun hook(observer: Observer<in T>) {
        val liveDataClass = LiveData::class.java

        val mObservers = liveDataClass.getDeclaredField("mObservers") // 存放Observer的Map
        mObservers.isAccessible = true

        // 调用SafeIterableMap的get()函数，获取ObserverWrapper对象
        val observerMap = mObservers.get(this)
        val observerMapClass = observerMap.javaClass
        val getMethod = observerMapClass.getDeclaredMethod("get", Object::class.java)
        getMethod.isAccessible = true

        val entry = getMethod.invoke(observerMap, observer)

        val observerWrapper = (entry as Map.Entry<*, *>).value
        val observerWrapperClass = observerWrapper!!.javaClass.superclass
        val lastVersionField = observerWrapperClass!!.getDeclaredField("mLastVersion")
        lastVersionField.isAccessible = true

        val versionField = liveDataClass.getDeclaredField("mVersion")
        versionField.isAccessible = true

        val mVersion = versionField.get(this)
        lastVersionField.set(observerWrapper, mVersion)
    }
}