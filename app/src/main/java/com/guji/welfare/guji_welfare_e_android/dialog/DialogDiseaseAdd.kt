package com.guji.welfare.guji_welfare_e_android.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.data.room.AppDatabase
import com.guji.welfare.guji_welfare_e_android.data.room.disease.entity.Disease
import com.guji.welfare.guji_welfare_e_android.databinding.DialogDiseaseAddBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DiseaseViewModel
import com.guji.welfare.guji_welfare_e_android.util.NetworkManager
import com.guji.welfare.guji_welfare_e_android.util.OnSingleClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class DialogDiseaseAdd(val roomDB: AppDatabase?) :
    BaseDialogFragment<DialogDiseaseAddBinding, DiseaseViewModel>(R.layout.dialog_disease_add) {

    override val viewModel: DiseaseViewModel by activityViewModels()

    override fun getViewModelClass(): Class<DiseaseViewModel> =
        DiseaseViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonClickEvent()
        buttonEnabled()
    }

    private fun buttonClickEvent() {
        with(binding) {
            buttonNo.setOnClickListener(OnSingleClickListener { dismiss() })
            buttonYes.setOnClickListener(OnSingleClickListener {
                if (NetworkManager.checkNetworkState(requireContext())) {
//                    viewModel.api
                }
                roomDB(editTextDisease.text.toString())
                viewModel.updateDiseaseData(roomDB!!.diseaseDao().getAll())
                dismiss()
            })
        }
    }

    private fun roomDB(disease: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val format = "yyyy-MM-dd"
            val simpleDateFormat = SimpleDateFormat(format)
            val time = Date(System.currentTimeMillis())
            val date: String = simpleDateFormat.format(time)
            roomDB!!.diseaseDao()
                .insertItem(Disease(index = 0, name = disease, date = date))
        }
    }

    private fun buttonEnabled() {
        with(binding) {
            buttonYes.isEnabled = false
            editTextDisease.addTextChangedListener {
                buttonYes.isEnabled = !it.isNullOrEmpty()
                Log.d("추가", it.isNullOrEmpty().toString() + " ${it.toString()}")
            }
        }
    }
}