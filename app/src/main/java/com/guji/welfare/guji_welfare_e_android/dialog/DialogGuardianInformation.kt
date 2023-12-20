package com.guji.welfare.guji_welfare_e_android.dialog

import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.data.dto.user.GuardianDto
import com.guji.welfare.guji_welfare_e_android.data.room.AppDatabase
import com.guji.welfare.guji_welfare_e_android.data.room.guardians.entity.Guardians
import com.guji.welfare.guji_welfare_e_android.databinding.DialogGuardianInformationBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.GuardianViewModel
import com.guji.welfare.guji_welfare_e_android.start.screen.toFormatPhoneNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogGuardianInformation(
    name: String,
    relationship: String,
    phoneNumber: String,
    index: Int,
    private val roomDB: AppDatabase?
) : BaseDialogFragment<DialogGuardianInformationBinding, GuardianViewModel>(R.layout.dialog_guardian_information) {
    private val name: String
    private val relationship: String
    private val phoneNumber: String
    private val index: Int

    init {
        this.name = name
        this.relationship = relationship
        this.phoneNumber = phoneNumber
        this.index = index
    }

    override val viewModel: GuardianViewModel by activityViewModels()

    override fun start() {
        with(binding) {
            textName.text = this@DialogGuardianInformation.name
            textRelationship.text = this@DialogGuardianInformation.relationship
            textPhoneNumber.text = this@DialogGuardianInformation.phoneNumber.toFormatPhoneNumber()
            buttonNo.setOnClickListener { dismiss() }
            buttonYes.setOnClickListener { TODO("보호자 정보 편집으로 넘어가기") }
            buttonDelete.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    deleteGuardian(
                        Guardians(
                            index = index,
                            info = relationship,
                            name = name,
                            telephoneNum = phoneNumber
                        )
                    )
                }

                dismiss()
            }
        }
    }

    private suspend fun deleteGuardian(guardians: Guardians) {
        var data: List<Guardians>
        CoroutineScope(Dispatchers.IO).launch {
            roomDB!!.guardiansDao().delete(guardians)
            data = roomDB.guardiansDao().getAll()
            viewModel.updateGuardiansData(data.map {
                GuardianDto(it.name, it.telephoneNum, it.info, it.index)
            }).join()
        }
    }
}