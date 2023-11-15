package com.guji.welfare.guji_welfare_e_android.dialog

import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogWelfareworkerRegistrationBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.WelfareworkerViewModel

class DialogWelfareworkerRegistration :
    BaseDialogFragment<DialogWelfareworkerRegistrationBinding, WelfareworkerViewModel>(
        R.layout.dialog_welfareworker_registration
    ) {

    override val viewModel: WelfareworkerViewModel by activityViewModels()
    override fun start() {
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