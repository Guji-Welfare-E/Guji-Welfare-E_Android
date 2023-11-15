package com.guji.welfare.guji_welfare_e_android.dialog

import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogGuardianInformationBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.GuardianViewModel

class DialogGuardianInformation(
    name: String, relationship: String, phoneNumber: String
) : BaseDialogFragment<DialogGuardianInformationBinding, GuardianViewModel>(R.layout.dialog_guardian_information) {
    private val name: String
    private val relationship: String
    private val phoneNumber: String

    init {
        this.name = name
        this.relationship = relationship
        this.phoneNumber = phoneNumber
    }

    override val viewModel: GuardianViewModel by activityViewModels()

    override fun start() {
        with(binding) {
            textName.text = this@DialogGuardianInformation.name
            textRelationship.text = this@DialogGuardianInformation.relationship
            textPhoneNumber.text = this@DialogGuardianInformation.phoneNumber
            buttonNo.setOnClickListener { TODO("보호자 정보 편집으로 넘어가기") }
            buttonYes.setOnClickListener { dismiss() }
        }
    }


}