package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.view.View
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogDiseaseAddBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DialogDiseaseViewModel

class DialogDiseaseAdd: BaseDialogFragment<DialogDiseaseAddBinding, DialogDiseaseViewModel>(R.layout.dialog_disease_add) {

    override fun getViewModelClass(): Class<DialogDiseaseViewModel> = DialogDiseaseViewModel::class.java


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

        }
    }
}