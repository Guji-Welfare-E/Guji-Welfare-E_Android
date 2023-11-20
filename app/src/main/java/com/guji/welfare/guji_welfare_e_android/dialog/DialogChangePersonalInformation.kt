package com.guji.welfare.guji_welfare_e_android.dialog

import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogChangePersonalInformationBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.UserDataViewModel

class DialogChangePersonalInformation: BaseDialogFragment<DialogChangePersonalInformationBinding, UserDataViewModel>(
    R.layout.dialog_change_personal_information) {

    override val viewModel: UserDataViewModel by activityViewModels()

    override fun start() {
        setEditText()
        clickListener()
    }

    private fun setEditText(){
        with(binding){
            textName.text = App.prefs.myName
            textDwelling.text = App.prefs.myDwelling
            textBirthday.text = App.prefs.myBirthday
        }
    }

    private fun clickListener(){
        with(binding){
            buttonOk.setOnClickListener {  }
            buttonNo.setOnClickListener { dismiss() }
        }
    }

}