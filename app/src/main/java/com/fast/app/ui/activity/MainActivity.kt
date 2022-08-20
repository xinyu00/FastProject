package com.fast.app.ui.activity

import android.widget.Toast
import com.fast.app.databinding.ActivityMainBinding
import com.fast.common.base.XBaseActivity
import com.fast.common.vm.BaseViewModel

class MainActivity : XBaseActivity<ActivityMainBinding,BaseViewModel>() {



    override fun loadData() {

    }

    override fun initViews() {
        mViewBinding.tvMain.setOnClickListener {
            Toast.makeText(this,"弹框",Toast.LENGTH_SHORT).show()
        }
    }

    override fun initEvent() {

    }
}