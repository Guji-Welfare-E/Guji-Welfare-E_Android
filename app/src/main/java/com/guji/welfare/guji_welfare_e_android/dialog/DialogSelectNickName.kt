package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogCheckChangeNicknameBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DialogCheckChangeNickNameViewModel

class DialogSelectNickName(nickname: String) :
    BaseDialogFragment<DialogCheckChangeNicknameBinding, DialogCheckChangeNickNameViewModel>(
        R.layout.dialog_check_change_nickname
    ) {

    private val nickname: String
    override fun getViewModelClass(): Class<DialogCheckChangeNickNameViewModel> =
        DialogCheckChangeNickNameViewModel::class.java

    init {
        this.nickname = nickname
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textNickname.text = nickname

            buttonYes.setOnClickListener { }
            buttonNo.setOnClickListener { dismiss() }
        }
    }

}