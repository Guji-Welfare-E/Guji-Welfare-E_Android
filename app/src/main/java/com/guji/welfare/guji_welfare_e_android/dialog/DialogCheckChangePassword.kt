package com.guji.welfare.guji_welfare_e_android.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.guji.welfare.guji_welfare_e_android.databinding.DialogCheckChangePasswordBinding

class DialogCheckChangePassword : DialogFragment() {

    private lateinit var binding: DialogCheckChangePasswordBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogCheckChangePasswordBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = binding.root
        builder.setView(dialogView)

        with(binding) {
            buttonOk.setOnClickListener { setDialogChangePassword() }
            buttonNo.setOnClickListener { dismiss() }
        }
        return builder.create()
    }

    private fun setDialogChangePassword() {
        Log.d("클릭","클릭")
        val dialogChangePassword = DialogChangePassword()
        with(dialogChangePassword) {
            isCancelable = false
            // parentFragmentManager를 사용하여 DialogFragment를 표시합니다.
            show(requireActivity().supportFragmentManager, "changePassword")
            dismiss()
        }
    }

}