package com.guji.welfare.guji_welfare_e_android.main.screen

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseActivity
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.account.screen.AccountActivity
import com.guji.welfare.guji_welfare_e_android.data.dto.user.DiseaseDisorder
import com.guji.welfare.guji_welfare_e_android.data.dto.user.UserDataDto
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.dialog.DialogChangePersonalInformation
import com.guji.welfare.guji_welfare_e_android.dialog.DialogCheckChangePassword
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardanInformationFix
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardianInformation
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardianInformationAdd
import com.guji.welfare.guji_welfare_e_android.dialog.DialogSelectNickName
import com.guji.welfare.guji_welfare_e_android.dialog.DialogCheckChangeNickName
import com.guji.welfare.guji_welfare_e_android.dialog.DialogDiseaseAdd
import com.guji.welfare.guji_welfare_e_android.dialog.DialogWelfareworkerRegistration
import com.guji.welfare.guji_welfare_e_android.main.adapter.DiseaseDisorderInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.GuardianInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import com.guji.welfare.guji_welfare_e_android.main.adapter.decoration.DiseaseDisorderInformationDecoration
import com.guji.welfare.guji_welfare_e_android.main.adapter.decoration.GuardianInformationDecoration
import com.guji.welfare.guji_welfare_e_android.main.service.TimeCheckService
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.MainViewModel


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main),
    DiseaseDisorderInformationListAdapter.OnItemClickListener,
    GuardianInformationListAdapter.OnItemClickListener {

    override val viewModel: MainViewModel by viewModels()


    private var guardianListData: List<GuardianInformationData> = listOf()
    private var diseaseDisorderInformationData: List<DiseaseDisorder> = listOf()
    private val guardiaInformationAdapter = GuardianInformationListAdapter()
    private val diseaseDisorderInformationListAdapter = DiseaseDisorderInformationListAdapter()
    private val diseaseDisorderInformationDecoration = DiseaseDisorderInformationDecoration()
    private val guardianInformationDecoration = GuardianInformationDecoration()


    private val permission: Array<String> = arrayOf(
        android.Manifest.permission.CALL_PHONE,
        android.Manifest.permission.SEND_SMS,
        android.Manifest.permission.READ_CONTACTS
    )

    override fun start() {
        installSplashScreen()

        ActivityCompat.requestPermissions(this, permission, 0)

        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        behavior.peekHeight = 1400

        setAdapter()
        setClickListener()
        switch()
        serviceStart()

        viewModel.getUserData()

        viewModel.guardianInformationList.observe(this@MainActivity) {
            checkEmpty(it)
            guardiaInformationAdapter.submitList(guardianListData)
        }

        viewModel.userData.observe(this@MainActivity) {
            diseaseDisorderInformationData = it.data.disease
            updateUI(it)
        }
    }

    private fun switch() {
        with(viewModel) {
            switchMyInformationStatus.observe(this@MainActivity) {
                if (!it) binding.layoutMyInformation.visibility = View.GONE
                else binding.layoutMyInformation.visibility = View.VISIBLE
            }

            switchGuardianInformationStatus.observe(this@MainActivity) {
                if (!it) binding.layoutGuardianInformation.visibility = View.GONE
                else binding.layoutGuardianInformation.visibility = View.VISIBLE
            }

            switchWelfareworkerInformationStatus.observe(this@MainActivity) {
                if (!it) binding.frameWelfareWorkerInformation.visibility = View.GONE
                else binding.frameWelfareWorkerInformation.visibility = View.VISIBLE
            }
        }
    }

    private fun autoSave() {
        val save = App.prefs
        with(binding) {
            save.myName = textMyName.text.toString()
            save.welfareworkerAffiliation = textWelfareWorkerAffiliation.text.toString()
            save.welfareWorkerName = textWelfareWorkerName.text.toString()
            save.welfareWorkerPhoneNumber = textWelfareWorkerPhoneNumber.text.toString()
            App.prefs.myDwelling = textMyDwelling.text.toString()
            App.prefs.myName = textMyName.text.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        autoSave()
    }


    private fun setAdapter() {
        with(binding) {

            recyclerViewGuardian.layoutManager = LinearLayoutManager(MainActivity())
            recyclerViewGuardian.adapter = guardiaInformationAdapter
            recyclerViewGuardian.addItemDecoration(guardianInformationDecoration)

            recyclerViewDiseaseDisorder.layoutManager = LinearLayoutManager(MainActivity())
            recyclerViewDiseaseDisorder.adapter = diseaseDisorderInformationListAdapter
            recyclerViewDiseaseDisorder.addItemDecoration(diseaseDisorderInformationDecoration)

            guardiaInformationAdapter.setItemClickListener(this@MainActivity)
            diseaseDisorderInformationListAdapter.setItemClickListener(this@MainActivity)
        }
    }

    private fun setClickListener() {
        with(binding) {
            buttonClose.setOnClickListener { binding.drawer.closeDrawer(GravityCompat.END) }
            buttonMenu.setOnClickListener { binding.drawer.openDrawer(GravityCompat.END) }

            //Add
            buttonGuardianAdd.setOnClickListener { setDialogGuardianInformationAdd() }
            buttonWelfareWorkerAdd.setOnClickListener { setDialogWelfareworkerRegistration() }
            buttonDiseaseDisorderAdd.setOnClickListener { setDialogDiseaseAdd() }

            //call
            buttonWelfareWorkerCall.setOnClickListener { setDialogSelectCall(App.prefs.welfareWorkerPhoneNumber.toString()) }


            //switch
            switchAutoBackground.setOnClickListener {
                //TODO("시간 마다 배경화면 변경 아 하기 싫다")
            }
            switchGuardianInformation.setOnClickListener {
                viewModel.switchGuardianInformationStatus.value =
                    binding.switchGuardianInformation.isChecked
            }
            switchMyInformation.setOnClickListener {
                viewModel.switchMyInformationStatus.value = binding.switchMyInformation.isChecked
            }
            switchWelfareworkerInformation.setOnClickListener {
                viewModel.switchWelfareworkerInformationStatus.value =
                    binding.switchWelfareworkerInformation.isChecked
            }

            //drawer button
            buttonChangeNickname.setOnClickListener { setDialogNickname(App.prefs.myNickname) }
            buttonChangeWelfareworker.setOnClickListener { setDialogWelfareworkerRegistration() }
            buttonChangePassword.setOnClickListener { setDialogCheckChangePassword() }
            buttonChangeInformation.setOnClickListener { changeInformation() }
            buttonSecession.setOnClickListener { secession() }
            buttonLogout.setOnClickListener { logout() }
        }
    }

    private fun logout() {
        App.prefs.remove()
        Intent(this@MainActivity, AccountActivity::class.java).also {
            startActivity(it)
            finish()
        }
        Toast.makeText(this@MainActivity, "로그아웃됨", Toast.LENGTH_SHORT).show()
    }

    private fun serviceStart() {
        Intent(this, TimeCheckService::class.java).also {
            startService(it)
        }
    }

    private fun secession() {
        //TODO("회원 탈퇴 로직")
    }

    private fun changeInformation() {
        setDialogChangePersonalInformation()
    }

    private fun updateUI(userDataDto: UserDataDto) {
        with(binding) {
            //my
            textMyName.text = userDataDto.data.name
            textMyDwelling.text = userDataDto.data.residence

            //disease
            if (userDataDto.data.disease.isNullOrEmpty()) emptyDiseaseDisorder.visibility =
                View.VISIBLE
            else {
                emptyDiseaseDisorder.visibility = View.GONE
                diseaseDisorderInformationListAdapter.submitList(userDataDto.data.disease)
            }
        }
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

    private fun setDialogChangePersonalInformation() {
        val dialogChangePersonalInformation = DialogChangePersonalInformation()
        with(dialogChangePersonalInformation) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "changePersonalInformation")
        }
    }

    private fun setDialogDiseaseAdd() {
        val dialogDiseaseAdd = DialogDiseaseAdd()
        with(dialogDiseaseAdd) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "diseaseAdd")
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

    override fun onClickDieaseInformation(v: View, position: Int) {

    }


    override fun onClick(v: View, position: Int) {
        setDialogGuardianInformation(position)
    }

    override fun onClickCall(v: View, position: Int) {
        setDialogSelectCall(position)
    }

}