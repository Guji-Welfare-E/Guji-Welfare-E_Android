package com.guji.welfare.guji_welfare_e_android.main.screen

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
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
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient.cookieManager
import com.guji.welfare.guji_welfare_e_android.data.room.AppDatabase
import com.guji.welfare.guji_welfare_e_android.data.room.disease.entity.Disease
import com.guji.welfare.guji_welfare_e_android.data.room.guardians.entity.Guardians
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.dialog.DialogChangePersonalInformation
import com.guji.welfare.guji_welfare_e_android.dialog.DialogCheckChangePassword
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardanInformationFix
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardianInformation
import com.guji.welfare.guji_welfare_e_android.dialog.DialogGuardianInformationAdd
import com.guji.welfare.guji_welfare_e_android.dialog.DialogSelectNickName
import com.guji.welfare.guji_welfare_e_android.dialog.DialogDiseaseAdd
import com.guji.welfare.guji_welfare_e_android.dialog.DialogSelectCall
import com.guji.welfare.guji_welfare_e_android.dialog.DialogWelfareworkerRegistration
import com.guji.welfare.guji_welfare_e_android.main.adapter.DiseaseDisorderInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.GuardianInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import com.guji.welfare.guji_welfare_e_android.main.adapter.decoration.DiseaseDisorderInformationDecoration
import com.guji.welfare.guji_welfare_e_android.main.adapter.decoration.GuardianInformationDecoration
import com.guji.welfare.guji_welfare_e_android.main.service.TimeCheckService
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

    private var roomDB: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        behavior.peekHeight = 1400

        ActivityCompat.requestPermissions(this, permission, 0)
    }

    override fun start() {
        roomDB = AppDatabase.getInstance(this)
        setAdapter()

        with(viewModel) {
            getUserData()
            userData.observe(this@MainActivity) {
                Log.d("userData","userData 변경 : $it")
                updateInformationUI(it)
                updateInformation(it)
            }

            if(userData.value != null){
                updateInformation(userData.value!!)
                updateInformationUI(userData.value!!)
            }


            CoroutineScope(Dispatchers.IO).launch {
                //RoomDB에 저장 되어 있는 값 꺼내오기
                setOffline()
            }
            updateWelfareworkerUI()
        }

        refresh()
//
//        updateDiseaseUI(diseaseDisorderInformationData)
//        updateGuardianUI(guardianListData)

        serviceStart()
        setClickListener()
        switch()

        with(diseaseViewModel) {
            disease.observe(this@MainActivity) {
                Log.d("질병", it.toString())
                diseaseDisorderInformationData = it.map { diseaseData ->
                    DiseaseDisorder(diseaseData.name, diseaseData.date)
                }
                updateDiseaseUI(diseaseDisorderInformationData)
            }
        }

        with(guardianViewModel) {
            guardian.observe(this@MainActivity) {
                guardianListData = it
                updateGuardianUI(guardianListData)
            }
        }

        Log.d("SharedPreferences",App.prefs.refreshToken)
        Log.d("SharedPreferences",App.prefs.accessToken)
        Log.d("SharedPreferences",App.prefs.welfareWorkerName)
        Log.d("SharedPreferences",App.prefs.welfareWorkerBelong)
        Log.d("SharedPreferences",App.prefs.welfareWorkerPhoneNumber)
        Log.d("SharedPreferences",App.prefs.myBirthday)
        Log.d("SharedPreferences",App.prefs.myNickname)
        Log.d("SharedPreferences",App.prefs.myDwelling)
        Log.d("SharedPreferences",App.prefs.myName)

    }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyInstance()
    }

    private fun refresh() {
        binding.refreshLayout.setOnRefreshListener {
            if (!isNetworkConnected(this@MainActivity)) {
                Toast.makeText(this, "네트워크에 연결 해주세요", Toast.LENGTH_SHORT).show()
                binding.refreshLayout.isRefreshing = false
            } else {
                with(viewModel) {
                    getUserData()
                    userData.observe(this@MainActivity) {
                        updateInformationUI(it)
                        updateInformation(it)
                    }

                    if(userData.value != null){
                        updateInformation(userData.value!!)
                        updateInformationUI(userData.value!!)
                    }
                }



                CoroutineScope(Dispatchers.IO).launch {
                    //RoomDB에 저장 되어 있는 값 꺼내오기
                    setOffline()
                }
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    private fun updateInformation(userDataDto: UserDataDto) {
        CoroutineScope(Dispatchers.IO).launch {
            val guardianData = userDataDto.data.guardian
            val diseaseData = userDataDto.data.disease

            if (guardianData != null) {
                roomDB!!.guardiansDao().deleteAll()
                var index = 0
                guardianData.map {
                    Log.d("guardians", it.toString())
                    roomDB!!.guardiansDao().insertItem(
                        Guardians(
                            index = index,
                            info = it.info,
                            name = it.name,
                            telephoneNum = it.telephoneNum
                        )
                    )
                    index++
                }
            } else {
                roomDB!!.guardiansDao().deleteAll()
            }

            if (diseaseData != null) {
                roomDB!!.diseaseDao().deleteAll()
                var index = 0
                diseaseData.map {
                    Log.d("disease", it.toString())
                    roomDB!!.diseaseDao()
                        .insertItem(Disease(date = it.createTime, name = it.name, index = index))
                    index++
                }
            } else {
                roomDB!!.diseaseDao().deleteAll()
            }
        }
    }


    /**
     *
     * */
    private suspend fun setOffline() {
        CoroutineScope(Dispatchers.IO).launch {
            //RoomDB에 저장 되어 있는 값 꺼내오기
            guardianListData = roomDB!!.guardiansDao().getAll().map {
                GuardianInformationData(it.name, it.telephoneNum, it.info)
            }

            diseaseDisorderInformationData = roomDB!!.diseaseDao().getAll().map {
                DiseaseDisorder(it.name, it.date)
            }

        }.join()

        Log.d("Room", "guardianListData : $guardianListData")
        Log.d("Room", "diseaseDisorderInformationData : $diseaseDisorderInformationData")

        withContext(Dispatchers.Main){
            updateDiseaseUI(diseaseDisorderInformationData)
            updateGuardianUI(guardianListData)
        }
    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
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

            with(viewModel) {
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

    private fun removeRoomDB() {
        CoroutineScope(Dispatchers.IO).launch {
            roomDB!!.guardiansDao().deleteAll()
            roomDB!!.diseaseDao().deleteAll()
        }
    }

    private fun changeInformation() {
        setDialogChangePersonalInformation()
    }

    private fun updateInformationUI(userDataDto: UserDataDto) {
        with(binding) {
            textMyName.text = userDataDto.data.name
            textMyDwelling.text = userDataDto.data.residence
            textMyNickname.text = userDataDto.data.nickName

            if (App.prefs.welfareWorkerName.isNotEmpty() && App.prefs.welfareWorkerPhoneNumber.isNotEmpty() && App.prefs.welfareWorkerBelong.isNotEmpty()) {
                layoutWelfareWorkerNull.visibility = View.GONE
                layoutWelfareWorker.visibility = View.VISIBLE

                textWelfareWorkerName.text = App.prefs.welfareWorkerName
                textWelfareWorkerPhoneNumber.text = App.prefs.welfareWorkerPhoneNumber
                textWelfareWorkerAffiliation.text = App.prefs.welfareWorkerBelong
            } else {
                layoutWelfareWorkerNull.visibility = View.VISIBLE
                layoutWelfareWorker.visibility = View.GONE
            }
        }
    }

    private fun updateWelfareworkerUI() {
        if (App.prefs.welfareWorkerName.isNotEmpty() && App.prefs.welfareWorkerPhoneNumber.isNotEmpty() && App.prefs.welfareWorkerBelong.isNotEmpty()) {
            with(binding) {
                layoutWelfareWorkerNull.visibility = View.GONE
                layoutWelfareWorker.visibility = View.VISIBLE

                textWelfareWorkerName.text = App.prefs.welfareWorkerName
                textWelfareWorkerPhoneNumber.text = App.prefs.welfareWorkerPhoneNumber
                textWelfareWorkerAffiliation.text = App.prefs.welfareWorkerBelong
            }
        } else {
            binding.layoutWelfareWorkerNull.visibility = View.VISIBLE
            binding.layoutWelfareWorker.visibility = View.GONE
        }
    }

    private fun updateDiseaseUI(diseaseDisorderData: List<DiseaseDisorder>) {
        with(binding) {
            if (diseaseDisorderData.isNullOrEmpty()) {
                emptyDiseaseDisorder.visibility = View.VISIBLE
            } else {
                emptyDiseaseDisorder.visibility = View.GONE
                diseaseDisorderInformationListAdapter.submitList(diseaseDisorderData)
            }
        }
    }

    private fun updateGuardianUI(guardianDisorderData: List<GuardianInformationData>) {
        with(binding) {
            if (guardianDisorderData.isNullOrEmpty()) {
                recyclerViewGuardian.visibility = View.GONE
                emptyGuardian.visibility = View.VISIBLE
            } else {
                guardianListData = guardianDisorderData
                recyclerViewGuardian.visibility = View.VISIBLE
                emptyGuardian.visibility = View.GONE
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
        Log.d("전화번호", phoneNumber)
        val dialogSelectCall = DialogSelectCall(phoneNumber)
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


    private fun logout() {
        App.prefs.remove()
        cookieManager.clear()
        removeRoomDB()
        Intent(this@MainActivity, AccountActivity::class.java).also {
            startActivity(it)
            finish()
        }
        Toast.makeText(this@MainActivity, "로그아웃됨", Toast.LENGTH_SHORT).show()
    }

    private fun secession() {
        App.prefs.remove()
        cookieManager.clear()
        removeRoomDB()
        Intent(this@MainActivity, AccountActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }


    override fun onClickDieaseInformation(v: View, position: Int) {
        Log.d("질병/장애 정보", position.toString())
    }

    override fun onClick(v: View, position: Int) {
        setDialogGuardianInformation(position)
    }

    override fun onClickCall(v: View, position: Int) {
        setDialogSelectCall(position)
    }

}