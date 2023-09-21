package com.guji.welfare.guji_welfare_e_android.main.screen

import androidx.fragment.app.viewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseFragment
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentDrawerBinding
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.BeaderViewModel

class DrawerFragment:BaseFragment<FragmentDrawerBinding, BeaderViewModel>(R.layout.fragment_drawer) {
    override val viewModel: BeaderViewModel by viewModels()

    override fun start() {

    }
}