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
import com.guji.welfare.guji_welfare_e_android.databinding.DialogGuardianInformationAddBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DialogGuardianInformationAddViewModel


class DialogGuardianInformationAdd :
    BaseDialogFragment<DialogGuardianInformationAddBinding, DialogGuardianInformationAddViewModel>(
        R.layout.dialog_guardian_information_add
    ) {

    private lateinit var name: String
    private lateinit var relationship: String
    private lateinit var phoneNumber: String
    lateinit var requestLauncher: ActivityResultLauncher<Intent>


    override fun getViewModelClass(): Class<DialogGuardianInformationAddViewModel> =
        DialogGuardianInformationAddViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonYes.setOnClickListener {
                name = textGuardianName.text.toString()
                relationship = textGuardianRelationship.text.toString()
                phoneNumber = textGuardianPhoneNumber.text.toString()
                dismiss()
            }

            buttonNo.setOnClickListener {
                dismiss()
            }

            buttonFindPhoneNumber.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                requestLauncher.launch(intent)
            }

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
                        Log.d("test", "cursor size : ${cursor?.count}")

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

}