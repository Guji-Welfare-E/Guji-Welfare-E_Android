package com.guji.welfare.guji_welfare_e_android.dialog

import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogCheckChangePasswordBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.UserDataViewModel

class DialogCheckChangePassword : BaseDialogFragment<DialogCheckChangePasswordBinding, UserDataViewModel>(R.layout.dialog_check_change_password) {
    override val viewModel: UserDataViewModel by activityViewModels()

    override fun start() {
        with(binding) {
            buttonOk.setOnClickListener { setDialogChangePassword(); dismiss()}
            buttonNo.setOnClickListener { dismiss() }
        }
    }


    private fun setDialogChangePassword() {
        val dialogChangePassword = DialogChangePassword()
        val fragmentManager = requireActivity().supportFragmentManager
        with(dialogChangePassword) {
            isCancelable = false
            // parentFragmentManager를 사용하여 DialogFragment를 표시합니다.
            show(fragmentManager, "changePassword")
        }
    }

}