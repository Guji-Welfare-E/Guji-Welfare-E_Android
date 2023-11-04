package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogChangePasswordBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.UserDataViewModel
import java.util.regex.Pattern

class DialogChangePassword: BaseDialogFragment<DialogChangePasswordBinding, UserDataViewModel>(
    R.layout.dialog_change_password) {

    override fun getViewModelClass(): Class<UserDataViewModel> = UserDataViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            buttonNo.setOnClickListener { dismiss() }
            buttonOk.setOnClickListener {
                if(isPasswordValid(textNewPassword.text.toString(),textCheckPassword.text.toString(),binding)){

                }
            }
        }
    }

    private fun isPasswordValid(password: String, checkPassword: String, binding: DialogChangePasswordBinding): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z]{5,15}$")
        val matcher = pattern.matcher(password)
        val samePassword: Boolean = password == checkPassword
        if(!matcher.matches()) binding.textErrorNewPassword.visibility = View.VISIBLE
        else binding.textErrorNewPassword.visibility = View.INVISIBLE
        if(!samePassword) binding.textErrorSamePassword.visibility = View.VISIBLE
        else binding.textErrorSamePassword.visibility = View.INVISIBLE
        return matcher.matches() && samePassword
    }
}