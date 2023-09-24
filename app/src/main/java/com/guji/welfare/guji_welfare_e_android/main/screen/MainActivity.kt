package com.guji.welfare.guji_welfare_e_android.main.screen

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseActivity
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardianInformation
import com.guji.welfare.guji_welfare_e_android.main.adapter.GuardianInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import com.guji.welfare.guji_welfare_e_android.main.adapter.decoration.GuardianInformationDecoration
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.MainViewModel


class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel: MainViewModel by viewModels()

    private val explanationData = arrayListOf<GuardianInformationData>()

    private val guardianInformationDecoration = GuardianInformationDecoration()
    private val guardiaInformationAdapter = GuardianInformationListAdapter()

    override fun start() {

        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        behavior.peekHeight = 450

        guardiaInformationAdapter.setItemClickListener(guardiaInformationAdapter)


        with(binding){
            recyclerView.layoutManager = LinearLayoutManager(MainActivity())
            recyclerView.adapter = guardiaInformationAdapter
            guardiaInformationAdapter.setItemClickListener(guardiaInformationAdapter)
            recyclerView.addItemDecoration(guardianInformationDecoration)
            buttonAdd.setOnClickListener {
                explanationData.add(GuardianInformationData(
                    name = "홍길동",
                    phoneNumber = "010-1234-5678",
                    relationship = "아들"
                ))
                if(explanationData.size == 0){
                    binding.recyclerView.visibility = View.GONE
                    binding.empty.visibility = View.VISIBLE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.empty.visibility = View.GONE
                    guardiaInformationAdapter.submitList(explanationData)
                }
            }

        }
        binding.buttonClose.setOnClickListener {
//            binding.mainDrawerView.
        }
        binding.button.setOnClickListener {
            setDialogGuardianInformation()
        }
    }

    private fun setDialogGuardianInformation(){
        val dialogGuardianInformation = DialogGuardianInformation("name","relationship","phoneNumber")
        dialogGuardianInformation.isCancelable = false
        dialogGuardianInformation.show(this.supportFragmentManager,"")
    }

}