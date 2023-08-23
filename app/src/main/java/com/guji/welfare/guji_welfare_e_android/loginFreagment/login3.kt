package com.guji.welfare.guji_welfare_e_android.loginFreagment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.guji.welfare.guji_welfare_e_android.MainActivity
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
            onAuthority()
            if (fragment != null) {
                requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
        binding.noBtn.setOnClickListener {
            System.exit(0)
        }
        binding.enterBtn.setOnClickListener {
            var fragment = requireActivity().supportFragmentManager.findFragmentByTag("login3")
            onAuthority()
            if (fragment != null) {
                requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
        }

        return binding.root
    }
    fun onAuthority(){
        var mainContext = (activity as MainActivity)
        val systemAlertWindwpermission = Manifest.permission.SYSTEM_ALERT_WINDOW

        if (ContextCompat.checkSelfPermission(mainContext, systemAlertWindwpermission) == PackageManager.PERMISSION_DENIED) {
            val SYSTEM_ALERT_WINDOW = 4
            ActivityCompat.requestPermissions(mainContext, arrayOf(systemAlertWindwpermission), SYSTEM_ALERT_WINDOW)
        }
    }


}