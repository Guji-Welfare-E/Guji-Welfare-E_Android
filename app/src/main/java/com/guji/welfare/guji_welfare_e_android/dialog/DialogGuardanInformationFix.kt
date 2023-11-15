package com.guji.welfare.guji_welfare_e_android.dialog

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseDialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogGuardianInformationFixBinding
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.GuardianViewModel

class DialogGuardanInformationFix(
    name: String, relationship: String, phoneNumber: String
) : BaseDialogFragment<DialogGuardianInformationFixBinding, GuardianViewModel>(R.layout.dialog_guardian_information_fix) {
    private val name: String
    private val relationship: String
    private val phoneNumber: String

    init {
        this.name = name
        this.relationship = relationship
        this.phoneNumber = phoneNumber
    }

    override val viewModel: GuardianViewModel by activityViewModels()

    override fun start() {
        with(binding) {
            textGuardianName.setText(this@DialogGuardanInformationFix.name)
            textGuardianRelationship.setText(this@DialogGuardanInformationFix.relationship)
            textGuardianPhoneNumber.setText(this@DialogGuardanInformationFix.phoneNumber)


            buttonNo.setOnClickListener { dismiss() }
            buttonYes.setOnClickListener {
                checkEmptyText(
                    textGuardianName.text.toString(),
                    textGuardianPhoneNumber.text.toString(),
                    textGuardianRelationship.text.toString(),
                    binding
                )
            }

            textGuardianName.addTextChangedListener {
                if (checkEmptyText(
                        textGuardianName.text,
                        textGuardianPhoneNumber.text,
                        textGuardianRelationship.text
                    )
                ) {
                    buttonYes.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                } else {
                    buttonYes.setBackgroundColor(Color.parseColor("#00E625"))
                }
            }
            textGuardianPhoneNumber.addTextChangedListener {
                if (checkEmptyText(
                        textGuardianName.text,
                        textGuardianPhoneNumber.text,
                        textGuardianRelationship.text
                    )
                ) {
                    buttonYes.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                } else {
                    buttonYes.setBackgroundColor(Color.parseColor("#00E625"))
                }


            }
            textGuardianRelationship.addTextChangedListener {
                if (checkEmptyText(
                        textGuardianName.text,
                        textGuardianPhoneNumber.text,
                        textGuardianRelationship.text
                    )
                ) {
                    buttonYes.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                } else {
                    buttonYes.setBackgroundColor(Color.parseColor("#00E625"))
                }

            }
        }
    }

    private fun checkEmptyText(
        name: Editable?,
        phoneNumber: Editable?,
        relationship: Editable?
    ): Boolean {
        return !name.isNullOrEmpty() && !phoneNumber.isNullOrEmpty() && !relationship.isNullOrEmpty()
    }

    private fun checkEmptyText(
        name: String,
        phoneNumber: String,
        relationship: String,
        binding: DialogGuardianInformationFixBinding
    ) {
        with(binding) {
            if (name.isEmpty()) textErrorName.visibility = View.VISIBLE
            else textErrorName.visibility = View.INVISIBLE
            if (phoneNumber.isEmpty()) textErrorPhoneNumber.visibility = View.VISIBLE
            else textErrorPhoneNumber.visibility = View.INVISIBLE
            if (relationship.isEmpty()) textErrorRelationship.visibility = View.VISIBLE
            else textErrorRelationship.visibility = View.INVISIBLE
        }
    }
}