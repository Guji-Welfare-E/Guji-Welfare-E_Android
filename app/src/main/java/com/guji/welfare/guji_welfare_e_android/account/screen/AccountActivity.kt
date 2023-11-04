package com.guji.welfare.guji_welfare_e_android.account.screen

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityAccountBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DiseaseViewModel

class AccountActivity : AppCompatActivity() {

    private lateinit var viewModel: DiseaseViewModel

    private val binding by lazy {
        ActivityAccountBinding.inflate(layoutInflater)
    }

    private val loginFragment by lazy {
        LoginFragment()
    }

    private val signupFragment by lazy {
        SignupFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DiseaseViewModel::class.java]
        transactionFragment(loginFragment)
    }


    private fun transactionFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}