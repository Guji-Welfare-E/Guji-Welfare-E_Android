package com.guji.welfare.guji_welfare_e_android.account.screen

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.account.viewmodel.AccountViewModel
import com.guji.welfare.guji_welfare_e_android.base.BaseActivity
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityAccountBinding

class AccountActivity: BaseActivity<ActivityAccountBinding, AccountViewModel>(
    R.layout.activity_account
) {
    override val viewModel: AccountViewModel by viewModels()

    private val loginFragment by lazy {
        LoginFragment()
    }

    private val signupFragment by lazy {
        SignupFragment()
    }
    override fun start() {
        transactionFragment(loginFragment)
    }


    private fun transactionFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}