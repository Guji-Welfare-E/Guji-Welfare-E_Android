package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogCheckChangePasswordBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DialogCheckChangePasswordViewModel

class DialogCheckChangeNickName(
    phoneNumber: String
) : BaseDialogFragment<DialogCheckChangePasswordBinding,DialogCheckChangePasswordViewModel>(R.layout.dialog_check_change_password) {
    private val phoneNumber: String

    init {
        this.phoneNumber = phoneNumber
    }

    override fun getViewModelClass(): Class<DialogCheckChangePasswordViewModel> = DialogCheckChangePasswordViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonNo.setOnClickListener { dismiss() }
            buttonOk.setOnClickListener { TODO("전화 함수 호출") }
        }
    }
}