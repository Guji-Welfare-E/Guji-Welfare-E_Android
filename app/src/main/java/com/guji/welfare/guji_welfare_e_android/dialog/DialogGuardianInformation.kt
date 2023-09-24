package com.guji.welfare.guji_welfare_e_android.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.databinding.DialogGuardianInformationBinding

class DialogGuardianInformation(
    name: String, relationship: String, phoneNumber: String
): DialogFragment() {
    private val name: String
    private val relationship: String
    private val phoneNumber: String

    init{
        this.name = name
        this.relationship = relationship
        this.phoneNumber = phoneNumber
    }

    private lateinit var binding: DialogGuardianInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogGuardianInformationBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(binding){
            name.text = this@DialogGuardianInformation.name
            relationship.text = this@DialogGuardianInformation.relationship
            phoneNumber.text = this@DialogGuardianInformation.phoneNumber
            buttonEdit.setOnClickListener {  }
            buttonOk.setOnClickListener { dismiss() }

        }
        return binding.root
    }
}