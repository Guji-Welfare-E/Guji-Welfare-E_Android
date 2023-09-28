package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogGuardianInformationBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DialogGuardianInformationViewModel

class DialogGuardianInformation(
    name: String, relationship: String, phoneNumber: String
) : BaseDialogFragment<DialogGuardianInformationBinding, DialogGuardianInformationViewModel>(R.layout.dialog_guardian_information) {
    private val name: String
    private val relationship: String
    private val phoneNumber: String

    init {
        this.name = name
        this.relationship = relationship
        this.phoneNumber = phoneNumber
    }

    override fun getViewModelClass(): Class<DialogGuardianInformationViewModel> =
        DialogGuardianInformationViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textName.text = this@DialogGuardianInformation.name
            textRelationship.text = this@DialogGuardianInformation.relationship
            textPhoneNumber.text = this@DialogGuardianInformation.phoneNumber
            buttonNo.setOnClickListener { TODO("보호자 정보 편집으로 넘어가기") }
            buttonYes.setOnClickListener { dismiss() }
        }
    }


}