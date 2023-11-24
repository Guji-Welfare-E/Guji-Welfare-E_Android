package com.guji.welfare.guji_welfare_e_android.account.screen

import android.content.Intent
import android.graphics.Typeface
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.guji.welfare.guji_welfare_e_android.App
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.account.viewmodel.AccountViewModel
import com.guji.welfare.guji_welfare_e_android.base.BaseFragment
import com.guji.welfare.guji_welfare_e_android.data.dto.account.LoginRequestDto
import com.guji.welfare.guji_welfare_e_android.data.dto.account.LoginResponseDto
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
    override val viewModel: AccountViewModel by activityViewModels()

    private var accountToken = ""
    private var refreshToken = ""

    private val pretendardBold: Typeface? by lazy {
        ResourcesCompat.getFont(requireContext(), R.font.pretendard_bold)
    }

    override fun start() {
        onClickEvent()
    }

    private fun onClickEvent() {
        with(binding) {
            buttonSignup.setOnClickListener(OnSingleClickListener {
                val signupFragment = SignupFragment()
                transactionFragment(signupFragment)
            })
            buttonLogin.setOnClickListener(OnSingleClickListener {
                val password = textPassword.text.toString()
                val phoneNumber = textPhoneNumber.text.toString()
                val loginDto = LoginRequestDto(
                    password = password,
                    telephoneNum = phoneNumber
                )
                login(loginDto)
            })
            buttonShowPassword.setOnClickListener {
                if (buttonShowPassword.isChecked) {
                    textPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    textPassword.typeface = pretendardBold
                } else {
                    textPassword.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    textPassword.typeface = pretendardBold
                }
            }
        }
    }

    private fun transactionFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun login(loginDto: LoginRequestDto) {
        RetrofitClient.api.login(loginDto).enqueue(object : Callback<LoginResponseDto> {
            override fun onResponse(
                call: Call<LoginResponseDto>,
                response: Response<LoginResponseDto>
            ) {
                if (response.isSuccessful) {
                    accountToken = cookieManager.getTokenCookieValue("access-token") ?: ""
                    refreshToken = cookieManager.getTokenCookieValue("refresh-token") ?: ""

                    if (binding.buttonMaintainLogin.isChecked) {
                        App.prefs.autoLogin = true
                        App.prefs.refreshToken = refreshToken
                        App.prefs.accessToken = accountToken
                    } else {
                        App.prefs.autoLogin = false
                        App.prefs.accessToken = accountToken
                    }

                    Intent(context, MainActivity::class.java).also {
                        startActivity(it)
                        requireActivity().finish()
                    }
                } else if (response.code() == 403) {
                    Toast.makeText(context, "비밀번호나 아이디가 틀렸습니다", Toast.LENGTH_SHORT).show()
                    binding.textPassword.setText("")
                    binding.textPhoneNumber.setText("")
                } else {
                    Toast.makeText(
                        context,
                        "로그인 실패 error code : ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                Toast.makeText(context, "인터넷 연결해주세요", Toast.LENGTH_SHORT).show()
                Log.e("애러", t.message.toString())
            }

        })
    }
}