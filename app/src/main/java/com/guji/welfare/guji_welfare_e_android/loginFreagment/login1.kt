package com.guji.welfare.guji_welfare_e_android.loginFreagment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentLogin1Binding

class login1 : Fragment() {
    lateinit var binding: FragmentLogin1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogin1Binding.inflate(inflater,container,false)
        binding.sumitBtn.setOnClickListener {
            var fragment = requireActivity().supportFragmentManager.findFragmentByTag("login1")
            if (fragment != null) {
                requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
        return binding.root
    }
// TODO: 권한 받기, 다음 프레그먼트로 이동
}