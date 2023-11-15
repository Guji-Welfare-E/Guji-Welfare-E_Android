package com.guji.welfare.guji_welfare_e_android.dialog

import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogCheckChangeNicknameBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.UserDataViewModel

class DialogSelectNickName(nickname: String) :
    BaseDialogFragment<DialogCheckChangeNicknameBinding, UserDataViewModel>(
        R.layout.dialog_check_change_nickname
    ) {

    private val nickname: String
    override val viewModel: UserDataViewModel by activityViewModels()

    init {
        this.nickname = nickname
    }

    override fun start() {
        with(binding) {
            textNickname.text = nickname

            buttonYes.setOnClickListener { setDialogChangeNickName(); dismiss() }
            buttonNo.setOnClickListener { dismiss() }
        }
    }

    private fun setDialogChangeNickName() {
        val dialogChangeNickName = DialogChangeNickName()
        val fragmentManager = requireActivity().supportFragmentManager
        dialogChangeNickName.show(fragmentManager, "changeNickName")
    }

}