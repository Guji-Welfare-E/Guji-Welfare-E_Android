package com.guji.welfare.guji_welfare_e_android.main.screen

import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseActivity
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.MainViewModel

class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel: MainViewModel by viewModels()

    override fun start() {
        binding.buttonMenu.setOnClickListener {
            binding.drawer.openDrawer(GravityCompat.END)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}