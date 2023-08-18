package com.guji.welfare.guji_welfare_e_android.ui.notification

import android.app.Notification
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentNotificationBinding
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentRegisterBinding
import com.guji.welfare.guji_welfare_e_android.viewmodel.NotificationViewModel
import com.guji.welfare.guji_welfare_e_android.viewmodel.RegisterViewModel

class NotificationFragment: Fragment() {


    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }
}