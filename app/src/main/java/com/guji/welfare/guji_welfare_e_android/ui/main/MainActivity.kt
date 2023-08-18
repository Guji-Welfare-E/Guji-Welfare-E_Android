package com.guji.welfare.guji_welfare_e_android.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.ui.home.HomeFragment
import com.guji.welfare.guji_welfare_e_android.ui.notification.NotificationFragment
import com.guji.welfare.guji_welfare_e_android.ui.register.RegisterFragment
import com.guji.welfare.guji_welfare_e_android.viewmodel.HomeViewModel

class MainActivity: AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(RegisterFragment())

        initBottomNav()
    }

    private fun initBottomNav() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.register -> { replaceFragment(RegisterFragment()); true }
                R.id.home -> { replaceFragment(HomeFragment());true }
                R.id.notification -> { replaceFragment(NotificationFragment());true }
                else -> { false }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.home_container, fragment)
        fragmentTransaction.commit()
    }
}