package com.guji.welfare.guji_welfare_e_android.start.screen

import androidx.activity.viewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseActivity
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityStartBinding
import com.guji.welfare.guji_welfare_e_android.start.viewmodel.StartViewModel

class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>(R.layout.activity_start) {
    override val viewModel: StartViewModel by viewModels()

    override fun start() {

    }
}