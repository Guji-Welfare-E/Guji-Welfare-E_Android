package com.guji.welfare.guji_welfare_e_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.loginFreagment.login1
import com.guji.welfare.guji_welfare_e_android.loginFreagment.login2
import com.guji.welfare.guji_welfare_e_android.loginFreagment.login3

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.main = this
        makeFragments()

    }
    fun makeFragments(){
        val login1 = supportFragmentManager.beginTransaction().add(R.id.login1, login1(), "login1")
        login1.commit()
        val login3 = supportFragmentManager.beginTransaction().add(R.id.login3, login3(), "login3")
        login3.commit()
        val login2 = supportFragmentManager.beginTransaction().add(R.id.login2, login2(), "login2")
        login2.commit()
    }



    //todo: 페이지3의 들어가기와 확인의 다른점 찾기
    //todo: logo를 top bar에 넣기
}