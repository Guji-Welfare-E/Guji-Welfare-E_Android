package com.guji.welfare.guji_welfare_e_android.dialog

import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogCheckChangeNicknameBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.UserDataViewModel

class DialogCheckChangeNickName() : BaseDialogFragment<DialogCheckChangeNicknameBinding, UserDataViewModel>(R.layout.dialog_check_change_nickname) {

    override val viewModel: UserDataViewModel by activityViewModels()

    override fun start() {
        buttonEvnet()
    }

    private fun buttonEvnet(){
        with(binding) {
            buttonNo.setOnClickListener { dismiss() }
            buttonYes.setOnClickListener {
                dismiss()
            }
        }
    }
}