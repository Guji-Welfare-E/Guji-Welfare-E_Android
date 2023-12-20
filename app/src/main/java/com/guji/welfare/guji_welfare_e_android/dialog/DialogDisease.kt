package com.guji.welfare.guji_welfare_e_android.dialog

import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.data.dto.user.DiseasesDto
import com.guji.welfare.guji_welfare_e_android.data.room.AppDatabase
import com.guji.welfare.guji_welfare_e_android.data.room.disease.entity.Disease
import com.guji.welfare.guji_welfare_e_android.databinding.DialogDiseaseBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DiseaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogDisease(disease: String, date: String, index: Int, private val roomDB: AppDatabase?) :
    BaseDialogFragment<DialogDiseaseBinding, DiseaseViewModel>(R.layout.dialog_disease) {
    override val viewModel: DiseaseViewModel by activityViewModels()

    private val disease: String
    private val date: String
    private val index: Int

    init {
        this.disease = disease
        this.date = date
        this.index = index
    }


    override fun start() {
        with(binding) {
            textName.text = disease
            textDate.text = date

            buttonYes.setOnClickListener { dismiss() }
            buttonDelete.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    deleteDisease()
                }

                dismiss()
            }
        }
    }

    private suspend fun deleteDisease() {
        var data: List<Disease>
        CoroutineScope(Dispatchers.IO).launch {
            roomDB!!.diseaseDao().delete(Disease(index = index, date = date, name = disease))
            data = roomDB.diseaseDao().getAll()
            viewModel.updateDiseaseData(data.map {
                DiseasesDto(it.date, it.name)
            }).join()
        }
    }

}