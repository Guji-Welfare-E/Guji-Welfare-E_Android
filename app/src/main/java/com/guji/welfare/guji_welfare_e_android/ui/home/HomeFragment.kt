package com.guji.welfare.guji_welfare_e_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentHomeBinding
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentRegisterBinding
import com.guji.welfare.guji_welfare_e_android.viewmodel.HomeViewModel
import com.guji.welfare.guji_welfare_e_android.viewmodel.RegisterViewModel

class HomeFragment: Fragment() {


    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }
}