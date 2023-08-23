package com.guji.welfare.guji_welfare_e_android.loginFreagment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.guji.welfare.guji_welfare_e_android.MainActivity
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentLogin1Binding
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentLogin2Binding


class login2 : Fragment() {
    lateinit var binding: FragmentLogin2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogin2Binding.inflate(inflater, container, false)
        binding.okBtn.setOnClickListener {
            var fragment = requireActivity().supportFragmentManager.findFragmentByTag("login2")
            onAuthority()
            if (fragment != null) {
                requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
        binding.noBtn.setOnClickListener {
            System.exit(0)
        }

        return binding.root
    }
    fun onAuthority(){
        var mainContext = (activity as MainActivity)
        val crossLocationPermission = Manifest.permission.ACCESS_COARSE_LOCATION

        if (ContextCompat.checkSelfPermission(mainContext, crossLocationPermission) == PackageManager.PERMISSION_DENIED) {
            val ACCESS_COARSE_LOCATION = 3
            ActivityCompat.requestPermissions(mainContext, arrayOf(crossLocationPermission), ACCESS_COARSE_LOCATION)
        }
    }

}