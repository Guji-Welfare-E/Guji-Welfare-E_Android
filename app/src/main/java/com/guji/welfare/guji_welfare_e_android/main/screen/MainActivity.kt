package com.guji.welfare.guji_welfare_e_android.main.screen

import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseActivity
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.dialog.DialogChangePassword
import com.guji.welfare.guji_welfare_e_android.dialog.DialogCheckChangePassword
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardanInformationFix
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardianInformation
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardianInformationAdd
import com.guji.welfare.guji_welfare_e_android.dialog.DialogNickname
import com.guji.welfare.guji_welfare_e_android.dialog.DialogSelectCall
import com.guji.welfare.guji_welfare_e_android.dialog.DialogWelfareworkerRegistration
import com.guji.welfare.guji_welfare_e_android.main.adapter.DiseaseDisorderInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.GuardianInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import com.guji.welfare.guji_welfare_e_android.main.adapter.decoration.GuardianInformationDecoration
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.MainViewModel


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main),
    DiseaseDisorderInformationListAdapter.OnItemClickListener,
    GuardianInformationListAdapter.OnItemClickListener {

    override val viewModel: MainViewModel by viewModels()

    private val explanationData = arrayListOf<GuardianInformationData>()
    private val guardianInformationDecoration = GuardianInformationDecoration()
    private val guardiaInformationAdapter = GuardianInformationListAdapter()

    private val permission: Array<String> = arrayOf(
        android.Manifest.permission.CALL_PHONE,
        android.Manifest.permission.SEND_SMS,
        android.Manifest.permission.READ_CONTACTS
    )


    override fun start() {
        ActivityCompat.requestPermissions(this, permission, 0)

        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        behavior.peekHeight = 450

        guardiaInformationAdapter.setItemClickListener(this)

        with(binding) {

            //recyclerView
            recyclerView.layoutManager = LinearLayoutManager(MainActivity())
            recyclerView.adapter = guardiaInformationAdapter
            recyclerView.addItemDecoration(guardianInformationDecoration)
            layoutWelfareWorker.visibility = View.GONE

            //My Information
            textMyName.text = App.prefs.myName?:""
            textMyNickname.text = App.prefs.nickname?:""
            textMyDwelling.text = App.prefs.dwelling?:""

            //drawerLayout open, close
            buttonClose.setOnClickListener { binding.drawer.closeDrawer(GravityCompat.END) }
            buttonMenu.setOnClickListener { binding.drawer.openDrawer(GravityCompat.END) }

            //Add
            buttonGuardianAdd.setOnClickListener { setDialogGuardianInformationAdd() }
            buttonWelfareWorkerAdd.setOnClickListener { }

            //call
            buttonWelfareWorkerCall.setOnClickListener { setDialogSelectCall(App.prefs.phoneNumber.toString()) }

            //switch
            switchAutoBackground.setOnClickListener {
                Log.d(
                    "스위치", switchAutoBackground.isChecked.toString()
                )
            }
            switchGuardianInformation.setOnClickListener {
                Log.d(
                    "스위치", switchGuardianInformation.isChecked.toString()
                )
            }
            switchMyInformation.setOnClickListener {
                Log.d(
                    "스위치", switchMyInformation.isChecked.toString()
                )
            }
            switchWelfareworkerInformation.setOnClickListener {
                Log.d(
                    "스위치", switchWelfareworkerInformation.isChecked.toString()
                )
            }

            //drawer button
            buttonChangeNickname.setOnClickListener { setDialogNickname(App.prefs.nickname.toString()) }
            buttonChangeWelfareworker.setOnClickListener { setDialogWelfareworkerRegistration() }
            buttonChangePassword.setOnClickListener { setDialogCheckChangePassword() }
            buttonSecession.setOnClickListener { }
            buttonChangeInformation.setOnClickListener { }

        }

        viewModel.guardianInformationList.observe(this) {
            checkEmpty(it)
            guardiaInformationAdapter.submitList(explanationData)
        }

        viewModel.welfareworker.observe(this) {
            Log.d("담당 복지사 등록", it)
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d("상태", "onPause")
    }

    private fun setDialogGuardianInformation(position: Int) {
        val name = guardianListData[position].name
        val relationship = guardianListData[position].relationship
        val phoneNumber = guardianListData[position].phoneNumber
        val dialogGuardianInformation = DialogGuardianInformation(name, relationship, phoneNumber)
        with(dialogGuardianInformation) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "guardianInformation")
        }
    }

    private fun setDialogGuardianInformationAdd() {
        val dialogGuardianInformationAdd = DialogGuardianInformationAdd()
        with(dialogGuardianInformationAdd) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "guardianInformationAdd")
        }
    }

    private fun setDialogGuardianInformationFix(
        name: String, phoneNumber: String, relationship: String
    ) {
        val dialogGuardianInformationFix =
            DialogGuardanInformationFix(name, phoneNumber, relationship)
        with(dialogGuardianInformationFix) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "guardianInformationFix")
        }
    }

    private fun setDialogWelfareworkerRegistration() {
        val dialogWelfareworkerRegistration = DialogWelfareworkerRegistration()
        with(dialogWelfareworkerRegistration) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "welfareworkerRegistration")
        }
    }

    private fun setDialogSelectCall(position: Int) {
        val phoneNumber = guardianListData[position].phoneNumber
        val dialogSelectCall = DialogCheckChangeNickName(phoneNumber)
        with(dialogSelectCall) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "selectCall")
        }
    }

    private fun setDialogSelectCall(phoneNumber: String) {
        val dialogSelectCall = DialogSelectCall(phoneNumber)
        with(dialogSelectCall) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "selectCall")
        }
    }

    private fun setDialogNickname(nickname: String) {
        val dialogNickname = DialogNickname(nickname)
        with(dialogNickname) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "nickname")
        }
    }

    private fun setDialogCheckChangePassword(){
        Log.d("클릭","클릭")
        val dialogCheckChangePassword = DialogCheckChangePassword()
        with(dialogCheckChangePassword){
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "checkChangePassword")
        }
    }

    fun call(phoneNumber: String) {
        startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:$phoneNumber")))
    }

    private fun checkEmpty(data: MutableList<GuardianInformationData>) {
        if (data.size == 0) {
            Log.d("checkEmpty", "숨기기")
            binding.recyclerView.visibility = View.GONE
            binding.empty.visibility = View.VISIBLE
        } else {
            Log.d("checkEmpty", "나타내기")
            binding.recyclerView.visibility = View.VISIBLE
            binding.empty.visibility = View.GONE
        }
    }



    override fun onClick(v: View, position: Int) {
        setDialogGuardianInformation(position)
    }

    override fun onClickCall(v: View, position: Int) {
        setDialogSelectCall(position)
    }

}