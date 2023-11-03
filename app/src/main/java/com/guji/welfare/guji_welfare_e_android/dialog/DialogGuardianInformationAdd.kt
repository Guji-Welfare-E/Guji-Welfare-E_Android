package com.guji.welfare.guji_welfare_e_android.dialog

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.data.room.AppDatabase
import com.guji.welfare.guji_welfare_e_android.data.room.guardians.entity.Guardians
import com.guji.welfare.guji_welfare_e_android.databinding.DialogGuardianInformationAddBinding
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.GuardianViewModel
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

    private val roomDB = roomDB
    override fun getViewModelClass(): Class<GuardianViewModel> =
        GuardianViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPhoneData()
        buttonEvent()
    }

    private fun buttonEvent(){
        with(binding) {
            buttonYes.setOnClickListener(OnSingleClickListener {
                name = textGuardianName.text.toString()
                relationship = textGuardianRelationship.text.toString()
                phoneNumber = textGuardianPhoneNumber.text.toString()
                if (name.isNotEmpty() && relationship.isNotEmpty() && phoneNumber.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        roomDB!!.guardiansDao()
                            .insertItem(Guardians(0, name, phoneNumber, relationship))
                    }
                    val guardiansData = roomDB!!.guardiansDao().getAll()
                    viewModel.updateGuardiansData(guardiansData.map {
                        GuardianInformationData(it.name, it.telephoneNum, it.info)
                    })
                    if (NetworkManager.checkNetworkState(requireContext())) {
                        val data = roomDB.guardiansDao().getAll()
                        viewModel.updateGuardiansData(data.map {
                            GuardianInformationData(it.name, it.info, it.telephoneNum)
                        })
                    }
                    dismiss()
                } else {
                    Log.d("상태","name :$name, relationship :$relationship, phoneNumber: $phoneNumber")
                }
            })
            buttonNo.setOnClickListener(OnSingleClickListener {
                dismiss()
            })
        }
    }

    private fun getPhoneData() {
        binding.buttonFindPhoneNumber.setOnClickListener (OnSingleClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            requestLauncher.launch(intent)
        })

        requestLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val cursor = requireActivity().contentResolver.query(
                        it.data!!.data!!,
                        arrayOf(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ),
                        null,
                        null,
                        null
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