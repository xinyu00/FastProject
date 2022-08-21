package com.fast.main.ui.activity

import android.widget.Toast
import com.fast.common.base.XBaseActivity
import com.fast.common.vm.BaseViewModel
import com.fast.main.databinding.ActivityMainBinding

class MainActivity : XBaseActivity<ActivityMainBinding,BaseViewModel>() {


    override fun initViews() {
        mViewBinding.tvMain.setOnClickListener {
            Toast.makeText(this,"弹框",Toast.LENGTH_SHORT).show()
        }
    }

}