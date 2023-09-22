package com.guji.welfare.guji_welfare_e_android.main.screen

import androidx.fragment.app.viewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseFragment
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentDrawerSettingBinding
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.SettingViewModel

class DrawerSettingFragment :
    BaseFragment<FragmentDrawerSettingBinding, SettingViewModel>(R.layout.fragment_drawer_setting) {
    override val viewModel: SettingViewModel by viewModels()

    override fun start() {

    }
}