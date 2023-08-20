package com.guji.welfare.guji_welfare_e_android.loginFreagment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentLogin1Binding
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentLogin3Binding

class login3 : Fragment() {
    lateinit var binding: FragmentLogin3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogin3Binding.inflate(inflater, container, false)
        binding.okBtn.setOnClickListener {
            var fragment = requireActivity().supportFragmentManager.findFragmentByTag("login3")
            // TODO: 권한 받기
            if (fragment != null) {
                requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
        binding.noBtn.setOnClickListener {
            System.exit(0)
        }
        binding.enterBtn.setOnClickListener {
            //TODO:ok랑 다르점 알아보기
            var fragment = requireActivity().supportFragmentManager.findFragmentByTag("login3")
            if (fragment != null) {
                requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
        }

        return binding.root
    }


}