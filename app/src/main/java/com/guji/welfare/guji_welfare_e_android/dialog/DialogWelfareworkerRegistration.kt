package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogWelfareworkerRegistrationBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DialogWelfareworkerRegistrationViewModel

class DialogWelfareworkerRegistration :
    BaseDialogFragment<DialogWelfareworkerRegistrationBinding, DialogWelfareworkerRegistrationViewModel>(
        R.layout.dialog_welfareworker_registration
    ) {
    override fun getViewModelClass(): Class<DialogWelfareworkerRegistrationViewModel> = DialogWelfareworkerRegistrationViewModel::class.java
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonYes.setOnClickListener {
                viewModel.setWelfareworkerPhoneNumber(textWelfareworkerPhoneNumber.text.toString())
                dismiss()
            }
            buttonNo.setOnClickListener { dismiss() }
        }
        with(viewModel){
            welfareworkerPhoneNumber.observe(this@DialogWelfareworkerRegistration){
                App.prefs.welfareWorkerPhoneNumber = it
            }
        }

    }
}