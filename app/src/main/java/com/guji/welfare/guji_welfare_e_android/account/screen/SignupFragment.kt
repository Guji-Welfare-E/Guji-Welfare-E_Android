package com.guji.welfare.guji_welfare_e_android.account.screen

import androidx.fragment.app.viewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.account.viewmodel.AccountViewModel
import com.guji.welfare.guji_welfare_e_android.base.BaseFragment
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentSignupBinding

class SignupFragment : BaseFragment<FragmentSignupBinding, AccountViewModel>(
R.layout.fragment_signup
) {
    override val viewModel: AccountViewModel by viewModels()
    override fun start() {

    }
}