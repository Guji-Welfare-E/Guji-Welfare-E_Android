package com.guji.welfare.guji_welfare_e_android.dialog

import android.view.View
import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogChangeNicknameBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.UserDataViewModel

class DialogChangeNickName :
    BaseDialogFragment<DialogChangeNicknameBinding, UserDataViewModel>(R.layout.dialog_change_nickname) {
    override val viewModel: UserDataViewModel by activityViewModels()
    override fun start() {
        with(binding) {
            textErrorEmpty.visibility = View.INVISIBLE
            buttonNo.setOnClickListener { dismiss() }
            buttonOk.setOnClickListener {
                if (textNickname.text.isNotEmpty()){
                    viewModel.setMyNickName(
                        textNickname.text.toString()
                    )
                    dismiss()
                } else {
                    textErrorEmpty.visibility = View.VISIBLE
                }
            }
        }
        viewModel.myNickname.observe(this){
            App.prefs.myNickname = it
        }
    }
}