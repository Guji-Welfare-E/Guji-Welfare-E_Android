package com.guji.welfare.guji_welfare_e_android.main.screen

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseActivity
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.account.screen.AccountActivity
import com.guji.welfare.guji_welfare_e_android.data.dto.user.DiseaseDisorder
import com.guji.welfare.guji_welfare_e_android.data.dto.user.UserDataDto
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient.cookieManager
import com.guji.welfare.guji_welfare_e_android.data.room.AppDatabase
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
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.DiseaseViewModel
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.GuardianViewModel
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.MainViewModel


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main),
    DiseaseDisorderInformationListAdapter.OnItemClickListener,
    GuardianInformationListAdapter.OnItemClickListener {

    override val viewModel: MainViewModel by viewModels()

    private lateinit var diseaseViewModel: DiseaseViewModel
    private lateinit var guardianViewModel: GuardianViewModel

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

    private var roomDB: AppDatabase? = null

    override fun start() {
        diseaseViewModel = ViewModelProvider(this)[DiseaseViewModel::class.java]
        guardianViewModel = ViewModelProvider(this)[GuardianViewModel::class.java]
        roomDB = AppDatabase.getInstance(this)

        installSplashScreen()

        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        behavior.peekHeight = 1400

        ActivityCompat.requestPermissions(this, permission, 0)

        setAdapter()
        setClickListener()
        switch()
        serviceStart()

        with(viewModel){
            getUserData()
            userData.observe(this@MainActivity) {
                updateMyInformationUI(it)
            }
        }

        with(diseaseViewModel){
            disease.observe(this@MainActivity){
                diseaseDisorderInformationData = it.map { diseaseData ->
                    DiseaseDisorder(diseaseData.name, diseaseData.date)
                }
                updateDiseaseUI(diseaseDisorderInformationData)
            }
        }

        with(guardianViewModel){
            guardian.observe(this@MainActivity){
                guardianListData = it
                updateGuardianUI(guardianListData)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyInstance()
    }

    private fun switch() {
        with(viewModel) {
            with(binding) {
                switchMyInformationStatus.observe(this@MainActivity) {
                    if (!it) layoutMyInformation.visibility = View.GONE
                    else layoutMyInformation.visibility = View.VISIBLE
                }

                switchGuardianInformationStatus.observe(this@MainActivity) {
                    if (!it) layoutGuardianInformation.visibility = View.GONE
                    else layoutGuardianInformation.visibility = View.VISIBLE
                }

                switchWelfareworkerInformationStatus.observe(this@MainActivity) {
                    if (!it) layoutWelfareWorkerInformation.visibility = View.GONE
                    else layoutWelfareWorkerInformation.visibility = View.VISIBLE
                }

                switchDiseaseDisorderInformationStatus.observe(this@MainActivity) {
                    if (!it) layoutDiseaseDisorder.visibility = View.GONE
                    else layoutDiseaseDisorder.visibility = View.VISIBLE
                }
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
            save.myDwelling = textMyDwelling.text.toString()
            save.myName = textMyName.text.toString()
        }
    }

    private fun setAdapter() {
        with(binding) {
            //Guardian
            recyclerViewGuardian.layoutManager = LinearLayoutManager(MainActivity())
            recyclerViewGuardian.adapter = guardiaInformationAdapter
            recyclerViewGuardian.addItemDecoration(guardianInformationDecoration)

            //Disorder
            recyclerViewDiseaseDisorder.layoutManager = LinearLayoutManager(MainActivity())
            recyclerViewDiseaseDisorder.adapter = diseaseDisorderInformationListAdapter
            recyclerViewDiseaseDisorder.addItemDecoration(diseaseDisorderInformationDecoration)

            //ClickListener
            guardiaInformationAdapter.setItemClickListener(this@MainActivity)
            diseaseDisorderInformationListAdapter.setItemClickListener(this@MainActivity)
        }
    }

    private fun setClickListener() {
        with(binding) {
            buttonClose.setOnClickListener { drawer.closeDrawer(GravityCompat.END) }
            buttonMenu.setOnClickListener { drawer.openDrawer(GravityCompat.END) }

            //Add
            buttonGuardianAdd.setOnClickListener { setDialogGuardianInformationAdd() }
            buttonWelfareWorkerAdd.setOnClickListener { setDialogWelfareworkerRegistration() }
            buttonDiseaseDisorderAdd.setOnClickListener { setDialogDiseaseAdd() }

            //call
            buttonWelfareWorkerCall.setOnClickListener { setDialogSelectCall(App.prefs.welfareWorkerPhoneNumber) }

            //switch
            switchAutoBackground.setOnClickListener {
                //TODO("시간 마다 배경화면 변경 아 하기 싫다")
            }
            with(viewModel){
                switchGuardianInformation.setOnClickListener {
                    switchGuardianInformationStatus.value =
                        switchGuardianInformation.isChecked
                }
                switchMyInformation.setOnClickListener {
                    switchMyInformationStatus.value =
                        switchMyInformation.isChecked
                }
                switchWelfareworkerInformation.setOnClickListener {
                    switchWelfareworkerInformationStatus.value =
                        switchWelfareworkerInformation.isChecked
                }
                switchDiseaseDisorderInformation.setOnClickListener {
                    switchDiseaseDisorderInformationStatus.value =
                        switchDiseaseDisorderInformation.isChecked
                }
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

    private fun serviceStart() {
        Intent(this, TimeCheckService::class.java).also {
            startService(it)
        }
    }

    private fun logout() {
        App.prefs.remove()
        cookieManager.clear()
        roomDB!!.guardiansDao().deleteAll()
        roomDB!!.diseaseDao().deleteAll()
        Intent(this@MainActivity, AccountActivity::class.java).also {
            startActivity(it)
            finish()
        }
        Toast.makeText(this@MainActivity, "로그아웃됨", Toast.LENGTH_SHORT).show()
    }

    private fun secession() {
        App.prefs.remove()
        cookieManager.clear()
        roomDB!!.guardiansDao().deleteAll()
        roomDB!!.diseaseDao().deleteAll()
        Intent(this@MainActivity, AccountActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun changeInformation() {
        setDialogChangePersonalInformation()
    }

    private fun updateMyInformationUI(userDataDto: UserDataDto) {
        with(binding) {
            textMyName.text = userDataDto.data.name
            textMyDwelling.text = userDataDto.data.residence
            textMyNickname.text = userDataDto.data.nickName
        }
    }

    private fun updateDiseaseUI(diseaseDisorderData: List<DiseaseDisorder>){
        with(binding){
            if (diseaseDisorderData.isNullOrEmpty()) emptyDiseaseDisorder.visibility =
                View.VISIBLE
            else {
                emptyDiseaseDisorder.visibility = View.GONE
                diseaseDisorderInformationListAdapter.submitList(diseaseDisorderData)
            }
        }
    }

    private fun updateGuardianUI(guardianDisorderData: List<GuardianInformationData>){
        with(binding){
            if (guardianDisorderData.isNullOrEmpty()) {
                recyclerViewGuardian.visibility = View.VISIBLE
                emptyGuardian.visibility = View.GONE
            }
            else {
                recyclerViewGuardian.visibility = View.GONE
                emptyGuardian.visibility = View.VISIBLE
                guardiaInformationAdapter.submitList(guardianListData)
            }
        }
    }

    private fun setDialogGuardianInformation(position: Int) {
        val data = guardianListData[position]
        val name = data.name
        val relationship = data.relationship
        val phoneNumber = data.phoneNumber
        val dialogGuardianInformation = DialogGuardianInformation(name, relationship, phoneNumber)
        with(dialogGuardianInformation) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "guardianInformation")
        }
    }

    private fun setDialogGuardianInformationAdd() {
        val dialogGuardianInformationAdd = DialogGuardianInformationAdd(roomDB)
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
        val dialogDiseaseAdd = DialogDiseaseAdd(roomDB)
        with(dialogDiseaseAdd) {
            isCancelable = false
            show(this@MainActivity.supportFragmentManager, "diseaseAdd")
        }
    }


    //Adapter Click Event fun
    override fun onClickDieaseInformation(v: View, position: Int) {

    }

    override fun onClick(v: View, position: Int) {
        setDialogGuardianInformation(position)
    }

    override fun onClickCall(v: View, position: Int) {
        setDialogSelectCall(position)
    }

}