package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogGuardianInformationAddBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DialogGuardianInformationAddViewModel


class DialogGuardianInformationAdd :
    BaseDialogFragment<DialogGuardianInformationAddBinding, DialogGuardianInformationAddViewModel>(
        R.layout.dialog_guardian_information_add
    ) {

    private lateinit var name: String
    private lateinit var relationship: String
    private lateinit var phoneNumber: String

    override fun getViewModelClass(): Class<DialogGuardianInformationAddViewModel> =
        DialogGuardianInformationAddViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonYes.setOnClickListener {
                name = textGuardianName.text.toString()
                relationship = textGuardianRelationship.text.toString()
                phoneNumber = textGuardianPhoneNumber.text.toString()
                dismiss()
            }

            buttonNo.setOnClickListener{
                dismiss()
            }

            buttonFindPhoneNumber.setOnClickListener {
                TODO("번호 찾기")
                findPhoneNumber()
            }

        }
    }

    private fun findPhoneNumber(){

    }

}