package com.guji.welfare.guji_welfare_e_android.start.screen

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION
import android.widget.Toast
import androidx.activity.viewModels
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.account.screen.AccountActivity
import com.guji.welfare.guji_welfare_e_android.base.BaseActivity
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityStartBinding
import com.guji.welfare.guji_welfare_e_android.main.screen.MainActivity
import com.guji.welfare.guji_welfare_e_android.start.viewmodel.StartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : BaseActivity<ActivityStartBinding, StartViewModel>(R.layout.activity_start) {
    override val viewModel: StartViewModel by viewModels()

    override fun start() {
        CoroutineScope(Main).launch{
            delay(1500)
            if(App.prefs.autoLogin){
                Toast.makeText(this@StartActivity, "자동로그인",Toast.LENGTH_SHORT).show()
                Intent(this@StartActivity, MainActivity::class.java).also {
                    it.addFlags(FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(it)
                    finish()
                }
            } else {
                Intent(this@StartActivity, AccountActivity::class.java).also {
                    it.addFlags(FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(it)
                    finish()
                }
            }
        }
    }
}