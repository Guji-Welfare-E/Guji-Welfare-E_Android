package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogChangeNicknameBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DialogChangeNickNameViewModel

class DialogChangeNickName :
    BaseDialogFragment<DialogChangeNicknameBinding, DialogChangeNickNameViewModel>(R.layout.dialog_change_nickname) {
    override fun getViewModelClass(): Class<DialogChangeNickNameViewModel> =
        DialogChangeNickNameViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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