package com.guji.welfare.guji_welfare_e_android.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogCheckChangePasswordBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.UserDataViewModel

class DialogCheckChangeNickName(
    phoneNumber: String
) : BaseDialogFragment<DialogCheckChangePasswordBinding, UserDataViewModel>(R.layout.dialog_check_change_password) {

    private val phoneNumber: String

    init {
        this.phoneNumber = phoneNumber
    }

    override fun getViewModelClass(): Class<UserDataViewModel> =
        UserDataViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonEvnet()
    }

    private fun buttonEvnet(){
        with(binding) {
            buttonNo.setOnClickListener { dismiss() }
            buttonOk.setOnClickListener {
                call(phoneNumber)
                dismiss()
            }
        }
    }

    private fun call(phoneNumber: String) {
        startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:$phoneNumber")))
    }
}