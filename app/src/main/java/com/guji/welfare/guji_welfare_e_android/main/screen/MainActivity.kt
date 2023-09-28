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
import com.guji.welfare.guji_welfare_e_android.dialog.DialogCheckChangePassword
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardanInformationFix
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardianInformation
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardianInformationAdd
import com.guji.welfare.guji_welfare_e_android.dialog.DialogSelectNickName
import com.guji.welfare.guji_welfare_e_android.dialog.DialogCheckChangeNickName
import com.guji.welfare.guji_welfare_e_android.dialog.DialogWelfareworkerRegistration
import com.guji.welfare.guji_welfare_e_android.main.adapter.DiseaseDisorderInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.GuardianInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.DiseaseDisorderInformationData
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import com.guji.welfare.guji_welfare_e_android.main.adapter.decoration.DiseaseDisorderInformationDecoration
import com.guji.welfare.guji_welfare_e_android.main.adapter.decoration.GuardianInformationDecoration
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.MainViewModel


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main),
    DiseaseDisorderInformationListAdapter.OnItemClickListener,
    GuardianInformationListAdapter.OnItemClickListener {

    override val viewModel: MainViewModel by viewModels()

    private var requestLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { contactUri ->
                    val cursor = contentResolver.query(
                        contactUri, arrayOf(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        ), null, null, null
                    )
                    cursor?.use {
                        if (it.moveToFirst()) {
                            val name = it.getString(0)
                            val phoneNumber = it.getString(1)
                            Log.d("연락처", "name: $name, phone: $phoneNumber")
                            // 여기에서 phoneNumber를 사용하거나 다른 처리를 수행할 수 있습니다.
                        }
                    }
                }
            }
        }


    private val guardianListData = arrayListOf<GuardianInformationData>()
    private val guardiaInformationAdapter = GuardianInformationListAdapter()
    private val guardianInformationDecoration = GuardianInformationDecoration()

    private val diseaseDisorderListData = arrayListOf<DiseaseDisorderInformationData>()
    private val diseaseDisorderInformationListAdapter = DiseaseDisorderInformationListAdapter()
    private val diseaseDisorderInformationDecoration = DiseaseDisorderInformationDecoration()

    private val permission: Array<String> = arrayOf(
        android.Manifest.permission.CALL_PHONE,
        android.Manifest.permission.SEND_SMS,
        android.Manifest.permission.READ_CONTACTS
    )


    override fun start() {
        ActivityCompat.requestPermissions(this, permission, 0)

        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        behavior.peekHeight = 1400

        guardiaInformationAdapter.setItemClickListener(this)
        diseaseDisorderInformationListAdapter.setItemClickListener(this)

        with(binding) {

            //recyclerView
            recyclerViewGuardian.layoutManager = LinearLayoutManager(MainActivity())
            recyclerViewGuardian.adapter = guardiaInformationAdapter
            recyclerViewGuardian.addItemDecoration(guardianInformationDecoration)

            recyclerViewDiseaseDisorder.layoutManager = LinearLayoutManager(MainActivity())
            recyclerViewDiseaseDisorder.adapter = diseaseDisorderInformationListAdapter
            recyclerViewDiseaseDisorder.addItemDecoration(diseaseDisorderInformationDecoration)


            layoutWelfareWorker.visibility = View.GONE


            //drawerLayout open, close
            buttonClose.setOnClickListener { binding.drawer.closeDrawer(GravityCompat.END) }
            buttonMenu.setOnClickListener { binding.drawer.openDrawer(GravityCompat.END) }


            //Add
            buttonGuardianAdd.setOnClickListener { setDialogGuardianInformationAdd() }
            buttonWelfareWorkerAdd.setOnClickListener { }


            //call
            buttonWelfareWorkerCall.setOnClickListener { setDialogSelectCall(App.prefs.welfareWorkerPhoneNumber.toString()) }


            //switch
            switchAutoBackground.setOnClickListener {

            }
            switchGuardianInformation.setOnClickListener {
                viewModel.switchGuardianInformationStatus.value = binding.switchGuardianInformation.isChecked
            }
            switchMyInformation.setOnClickListener {
                viewModel.switchMyInformationStatus.value = binding.switchMyInformation.isChecked
            }
            switchWelfareworkerInformation.setOnClickListener {
                viewModel.switchWelfareworkerInformationStatus.value = binding.switchWelfareworkerInformation.isChecked
            }


            //drawer button
            buttonChangeNickname.setOnClickListener { setDialogNickname(App.prefs.myNickname.toString()) }
            buttonChangeWelfareworker.setOnClickListener { setDialogWelfareworkerRegistration() }
            buttonChangePassword.setOnClickListener { setDialogCheckChangePassword() }
            buttonChangeInformation.setOnClickListener { }
            buttonSecession.setOnClickListener { }

        }

        binding.textMyName.text = App.prefs.myName ?: ""
        binding.textMyNickname.text = App.prefs.myNickname ?: ""
        binding.textMyDwelling.text = App.prefs.myDwelling ?: ""

        with(viewModel){
            myName.observe(this@MainActivity){
                App.prefs.myName = binding.textMyName.text.toString()
            }

            myDwelling.observe(this@MainActivity){
                App.prefs.myDwelling = binding.textMyDwelling.text.toString()
            }


            welfareworkerName.observe(this@MainActivity){
                App.prefs.welfareWorkerName = binding.textWelfareWorkerName.text.toString()
            }
            welfareworkerPhoneNumber.observe(this@MainActivity){
                App.prefs.welfareWorkerPhoneNumber = binding.textWelfareWorkerPhoneNumber.text.toString()
            }
            welfareworkerAffiliation.observe(this@MainActivity){
                App.prefs.welfareworkerAffiliation = binding.textWelfareWorkerAffiliation.text.toString()
            }

            viewModel.switchMyInformationStatus.observe(this@MainActivity){
                if (!it) binding.layoutMyInformation.visibility = View.GONE
                else binding.layoutMyInformation.visibility = View.VISIBLE
            }

            viewModel.switchGuardianInformationStatus.observe(this@MainActivity){
                if (!it) binding.layoutGuardianInformation.visibility = View.GONE
                else binding.layoutGuardianInformation.visibility = View.VISIBLE
            }

            viewModel.switchWelfareworkerInformationStatus.observe(this@MainActivity){
                if (!it) binding.frameWelfareWorkerInformation.visibility = View.GONE
                else binding.frameWelfareWorkerInformation.visibility = View.VISIBLE
            }

        }

        viewModel.guardianInformationList.observe(this) {
            checkEmpty(it)
            guardiaInformationAdapter.submitList(guardianListData)
        }

        viewModel.welfareworkerName.observe(this) {

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
        val dialogSelectCall = DialogCheckChangeNickName(phoneNumber)
        with(dialogSelectCall) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "selectCall")
        }
    }

    private fun setDialogNickname(nickname: String) {
        val dialogNickname = DialogSelectNickName(nickname)
        with(dialogNickname) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "nickname")
        }
    }

    private fun setDialogCheckChangePassword() {
        val dialogCheckChangePassword = DialogCheckChangePassword()
        with(dialogCheckChangePassword) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "checkChangePassword")
        }
    }

    fun call(phoneNumber: String) {
        startActivity(Intent("android.intent.action.CALL", Uri.parse("tel:$phoneNumber")))
    }

    private fun checkEmpty(data: MutableList<GuardianInformationData>) {
        if (data.size == 0) {
            binding.recyclerViewGuardian.visibility = View.GONE
            binding.emptyGuardian.visibility = View.VISIBLE
        } else {
            binding.recyclerViewGuardian.visibility = View.VISIBLE
            binding.emptyGuardian.visibility = View.GONE
        }
    }
    fun changePassword(password: String) {

    }



    override fun onClick(v: View, position: Int) {
        setDialogGuardianInformation(position)
    }

    override fun onClickCall(v: View, position: Int) {
        setDialogSelectCall(position)
    }

}