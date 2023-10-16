package com.guji.welfare.guji_welfare_e_android.account.screen

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.account.viewmodel.AccountViewModel
import com.guji.welfare.guji_welfare_e_android.base.BaseFragment
import com.guji.welfare.guji_welfare_e_android.data.dto.account.LoginRequestDto
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient.cookieManager
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentLoginBinding
import com.guji.welfare.guji_welfare_e_android.main.screen.MainActivity
import com.guji.welfare.guji_welfare_e_android.util.OnSingleClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : BaseFragment<FragmentLoginBinding, AccountViewModel>(
    R.layout.fragment_login
) {
    override val viewModel: AccountViewModel by viewModels()
    override fun start() {
        with(binding) {
            buttonSignup.setOnClickListener(OnSingleClickListener {
                val signupFragment = SignupFragment()
                transactionFragment(signupFragment)
            })
            buttonLogin.setOnClickListener(OnSingleClickListener {
                val password = textPassword.text.toString()
                val phoneNumber = textPhoneNumber.text.toString()
                login(password, phoneNumber)
            })
        }
    }


    private fun transactionFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun login(password: String, phoneNumber: String) {
        val loginDto = LoginRequestDto(
            password, phoneNumber
        )
        RetrofitClient.api.login(loginDto).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    var accountToken = ""
                    var refreshToken = ""
                    val cookies = cookieManager.cookieStore.cookies
                    for (cookie in cookies) {
                        if (cookie.name == "account-token") accountToken = cookie.value
                        if (cookie.name == "refresh-token") refreshToken = cookie.value
                    }
                    Log.d("Cookie", "AccountToken : $accountToken, RefreshToken : $refreshToken")
                    App.prefs.accessToken = accountToken

                    if (binding.buttonMaintainLogin.isChecked) {
                        App.prefs.autoLogin = true
                        App.prefs.refreshToken = refreshToken
                    }

                    Intent(context, MainActivity::class.java).also {
                        startActivity(it)
                        requireActivity().finish()
                    }

                } else if (response.code() == 403) {
                    Toast.makeText(context, "비밀번호나 아이디가 틀렸습니다", Toast.LENGTH_SHORT).show()
                    with(binding) {
                        textPassword.setText("")
                        textPhoneNumber.setText("")
                    }
                } else {
                    Toast.makeText(
                        context,
                        "로그인 실패 error code : ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Toast.makeText(context, "인터넷 연결해주세요", Toast.LENGTH_SHORT).show()
            }

        })
    }
}