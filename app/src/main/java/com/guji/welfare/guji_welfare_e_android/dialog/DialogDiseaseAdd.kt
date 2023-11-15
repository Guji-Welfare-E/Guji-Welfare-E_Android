package com.guji.welfare.guji_welfare_e_android.dialog

import android.util.Log
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

    override fun start() {
        buttonClickEvent()
        buttonEnabled()
        with(viewModel) {
            disease.observe(this@DialogDiseaseAdd) {
                Log.d("질병",it.toString())
//                diseaseDisorderInformationData = it.map { diseaseData ->
//                    DiseaseDisorder(diseaseData.name, diseaseData.date)
//                }
//                updateDiseaseUI(diseaseDisorderInformationData)
            }
        }
    }

    private fun buttonClickEvent() {
        with(binding) {
            buttonNo.setOnClickListener(OnSingleClickListener { dismiss() })
            buttonYes.setOnClickListener(OnSingleClickListener {
                if (NetworkManager.checkNetworkState(requireContext())) {

                }
                CoroutineScope(Dispatchers.IO).launch {
                    roomDB(editTextDisease.text.toString())
                }
                dismiss()
            })
        }
    }

    private fun roomDB(disease: String) {
        val format = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(format)
        val time = Date(System.currentTimeMillis())
        val date: String = simpleDateFormat.format(time)
        val dataSize = viewModel.getDataSize()
        viewModel.insertData(Disease(dataSize, disease, date))
    }

    private fun buttonEnabled() {
        with(binding) {
            buttonYes.isEnabled = false
            editTextDisease.addTextChangedListener {
                buttonYes.isEnabled = !it.isNullOrEmpty()
            }
        }
    }
}