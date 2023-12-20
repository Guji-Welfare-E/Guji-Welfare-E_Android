package com.guji.welfare.guji_welfare_e_android.dialog

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogSelectCallBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.CallViewModel
import com.guji.welfare.guji_welfare_e_android.start.screen.toFormatPhoneNumber
import com.guji.welfare.guji_welfare_e_android.util.OnSingleClickListener

class DialogSelectCall(val phoneNumber: String) :
    BaseDialogFragment<DialogSelectCallBinding, CallViewModel>(R.layout.dialog_select_call) {

    override val viewModel: CallViewModel by activityViewModels()

    override fun start() {
        with(binding) {
            phoneNumber.text = "${this@DialogSelectCall.phoneNumber.toFormatPhoneNumber()}에 연결하시겠습니까?"

            buttonNo.setOnClickListener(OnSingleClickListener {
                dismiss()
            })
            buttonYes.setOnClickListener(OnSingleClickListener {
                call(this@DialogSelectCall.phoneNumber)
                dismiss()
            })
        }
    }

    private fun call(phoneNumber: String) {
        startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:$phoneNumber")))
    }
}