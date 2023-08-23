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
import androidx.fragment.app.FragmentManager
import com.guji.welfare.guji_welfare_e_android.MainActivity
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
            onAuthority()
            if (fragment != null) {
                requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
        return binding.root
    }
// TODO: 권한 받기, 다음 프레그먼트로 이동
    fun onAuthority(){
        var mainContext = (activity as MainActivity)
        val ACCESS_FINE_LOCATIONPermission = Manifest.permission.ACCESS_FINE_LOCATION
        val sendSMSPermission = Manifest.permission.SEND_SMS
        val permission = Manifest.permission.ACCESS_NOTIFICATION_POLICY

        if (ContextCompat.checkSelfPermission(mainContext, sendSMSPermission) == PackageManager.PERMISSION_DENIED) {
            val REQUEST_SMS_PERMISSION = 0
            ActivityCompat.requestPermissions(mainContext, arrayOf(sendSMSPermission), REQUEST_SMS_PERMISSION)
        }
        if (ContextCompat.checkSelfPermission(mainContext, ACCESS_FINE_LOCATIONPermission) == PackageManager.PERMISSION_DENIED) {
            val REQUEST_LOCATION_PERMISSION = 1
            ActivityCompat.requestPermissions(mainContext, arrayOf(ACCESS_FINE_LOCATIONPermission), REQUEST_LOCATION_PERMISSION)
        }
        if (ContextCompat.checkSelfPermission(mainContext, permission) == PackageManager.PERMISSION_DENIED) {
            val REQUEST_NOTIFICATION_POLICY_PERMISSION = 2
            ActivityCompat.requestPermissions(mainContext, arrayOf(permission), REQUEST_NOTIFICATION_POLICY_PERMISSION)
        }
    }
}