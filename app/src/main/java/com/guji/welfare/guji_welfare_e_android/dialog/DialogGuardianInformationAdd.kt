package com.guji.welfare.guji_welfare_e_android.dialog

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.data.dto.user.GuardianDto
import com.guji.welfare.guji_welfare_e_android.data.room.AppDatabase
import com.guji.welfare.guji_welfare_e_android.data.room.guardians.entity.Guardians
import com.guji.welfare.guji_welfare_e_android.databinding.DialogGuardianInformationAddBinding
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.GuardianViewModel
import com.guji.welfare.guji_welfare_e_android.util.NetworkManager
import com.guji.welfare.guji_welfare_e_android.util.OnSingleClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DialogGuardianInformationAdd(roomDB: AppDatabase?) :
    BaseDialogFragment<DialogGuardianInformationAddBinding, GuardianViewModel>(
        R.layout.dialog_guardian_information_add
    ) {

    private lateinit var name: String
    private lateinit var relationship: String
    private lateinit var phoneNumber: String
    private lateinit var requestLauncher: ActivityResultLauncher<Intent>

    override val viewModel: GuardianViewModel by activityViewModels()

    private val roomDB = roomDB
    override fun start() {
        getPhoneData()
        buttonEvent()
    }

    private fun buttonEvent() {
        with(binding) {
            buttonYes.setOnClickListener(OnSingleClickListener {
                name = textGuardianName.text.toString()
                relationship = textGuardianRelationship.text.toString()
                phoneNumber = textGuardianPhoneNumber.text.toString()
                if (name.isNotEmpty() && relationship.isNotEmpty() && phoneNumber.isNotEmpty()) {
                    updateGuardian()
                    dismiss()
                } else {
                    Log.d(
                        "상태", "name :$name, relationship :$relationship, phoneNumber: $phoneNumber"
                    )
                }
            })
            buttonNo.setOnClickListener(OnSingleClickListener {
                dismiss()
            })
        }
    }

    private fun updateGuardian() {
        CoroutineScope(Dispatchers.IO).launch {
            var guardiansData = roomDB!!.guardiansDao().getAll()
            roomDB.guardiansDao()
                .insertItem(Guardians(guardiansData.size, name, phoneNumber, relationship))
            guardiansData = roomDB.guardiansDao().getAll()
            viewModel.updateGuardiansData(guardiansData.map {
                GuardianInformationData(it.name, it.telephoneNum, it.info)
            })
            //네트워크에 연결 되어 있으면
            if (NetworkManager.checkNetworkState(requireContext())) {
                //서버로 값 전송
                viewModel.updateGuardiansData(guardiansData.map {
                    GuardianDto(it.name, it.telephoneNum, it.info, it.index)
                })
            }
        }
    }

    private fun getPhoneData() {
        binding.buttonFindPhoneNumber.setOnClickListener(OnSingleClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            requestLauncher.launch(intent)
        })

        requestLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val cursor = requireActivity().contentResolver.query(
                        it.data!!.data!!, arrayOf(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ), null, null, null
                    )

                    if (cursor!!.moveToFirst()) {
                        val name = cursor.getString(0)
                        val phone = cursor.getString(1)
                        with(binding) {
                            textGuardianName.setText(name)
                            textGuardianPhoneNumber.setText(phone)
                        }
                    }
                }
            }
    }

}