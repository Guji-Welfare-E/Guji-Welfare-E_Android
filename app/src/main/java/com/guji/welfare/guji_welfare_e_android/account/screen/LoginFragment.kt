package com.guji.welfare.guji_welfare_e_android.account.screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.account.viewmodel.AccountViewModel
import com.guji.welfare.guji_welfare_e_android.base.BaseFragment
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding, AccountViewModel>(
    R.layout.fragment_login
) {
    override val viewModel: AccountViewModel by viewModels()
    override fun start() {
        with(binding) {
            buttonSignup.setOnClickListener {
                val signupFragment = SignupFragment()
                transactionFragment(signupFragment)
            }
            buttonLogin.setOnClickListener { }
        }
    }


    private fun transactionFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}