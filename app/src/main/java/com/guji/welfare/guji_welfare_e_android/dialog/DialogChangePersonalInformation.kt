package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogChangePersonalInformationBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.UserDataViewModel

class DialogChangePersonalInformation: BaseDialogFragment<DialogChangePersonalInformationBinding, UserDataViewModel>(
    R.layout.dialog_change_personal_information) {

    override fun getViewModelClass(): Class<UserDataViewModel> = UserDataViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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