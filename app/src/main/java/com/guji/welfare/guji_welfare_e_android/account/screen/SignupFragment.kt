package com.guji.welfare.guji_welfare_e_android.account.screen

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.account.viewmodel.AccountViewModel
import com.guji.welfare.guji_welfare_e_android.base.BaseFragment
import com.guji.welfare.guji_welfare_e_android.data.dto.StatusDto
import com.guji.welfare.guji_welfare_e_android.data.dto.account.SignupRequestDto
import com.guji.welfare.guji_welfare_e_android.data.network.RetrofitClient
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentSignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : BaseFragment<FragmentSignupBinding, AccountViewModel>(
    R.layout.fragment_signup
) {
    override val viewModel: AccountViewModel by viewModels()
    private val loginFragment = LoginFragment()

    private var name: String = ""
    private var birth: String = ""
    private var phoneNumber: String = ""
    private var dwelling: String = ""
    private var password: String = ""
    private var passwordCheck: String = ""
    override fun start() {
        consent()
        warningMessage()
        checkData()
        with(binding) {
            buttonSignup.isEnabled = false
            buttonSignup.setOnClickListener {
                signup(
                    SignupRequestDto(
                        name = name,
                        birth = birth,
                        telephoneNum = phoneNumber,
                        password = password,
                        residence = dwelling
                    )
                )
            }

            buttonLogin.setOnClickListener {
                transactionFragment(loginFragment)
            }
        }
    }

    private fun transactionFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }

    private fun signup(signupRequestDto: SignupRequestDto) {
        RetrofitClient.api.signup(signupRequestDto).enqueue(object : Callback<StatusDto> {
            override fun onResponse(call: Call<StatusDto>, response: Response<StatusDto>) {
                if (response.isSuccessful) {
                    transactionFragment(loginFragment)
                } else {
                    Log.e("애러",response.code().toString())
                    Toast.makeText(context, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<StatusDto>, t: Throwable) {
                Toast.makeText(context, "인터넷 연결해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun consent() {
        with(binding) {
            button0.setOnClickListener {
                if (button0.isChecked) {
                    button1.isChecked = true
                    button2.isChecked = true
                    button3.isChecked = true
                    button4.isChecked = true
                } else {
                    button1.isChecked = false
                    button2.isChecked = false
                    button3.isChecked = false
                    button4.isChecked = false
                }
                checkData()
            }
            button1.setOnClickListener { checkData() }
            button2.setOnClickListener { checkData() }
            button3.setOnClickListener { checkData() }
            button4.setOnClickListener { checkData() }
        }
    }

    private fun warningMessage() {
        with(binding) {
            edittextName.addTextChangedListener {
                name = edittextName.text.toString()
                if (name != "") textNameWarningMessage.visibility = View.INVISIBLE
                else textNameWarningMessage.visibility = View.VISIBLE
                checkData()
            }
            edittextPhoneNumber.addTextChangedListener {
                phoneNumber = edittextPhoneNumber.text.toString()
                if (phoneNumber != "") phoneNumberWarningMessage.visibility = View.INVISIBLE
                else phoneNumberWarningMessage.visibility = View.VISIBLE
                checkData()
            }
            edittextBirthday.addTextChangedListener {
                birth = edittextBirthday.text.toString()
                if (birth != "") birthdayWarningMessage.visibility = View.INVISIBLE
                else birthdayWarningMessage.visibility = View.VISIBLE
                checkData()
            }
            edittextDwelling.addTextChangedListener {
                dwelling = edittextDwelling.text.toString()
                if (dwelling != "") dwellingWarningMessage.visibility = View.INVISIBLE
                else dwellingWarningMessage.visibility = View.VISIBLE
                checkData()
            }
            edittextPassword.addTextChangedListener {
                password = edittextPassword.text.toString()
                if (password != "") passwordWarningMessage.visibility = View.INVISIBLE
                else passwordWarningMessage.visibility = View.VISIBLE

                if (passwordCheck == password) passwordCheckWarningMessage.visibility =
                    View.INVISIBLE
                else passwordCheckWarningMessage.visibility = View.VISIBLE

                checkData()
            }
            edittextPasswordCheck.addTextChangedListener {
                passwordCheck = edittextPasswordCheck.text.toString()
                if (passwordCheck == password) passwordCheckWarningMessage.visibility =
                    View.INVISIBLE
                else passwordCheckWarningMessage.visibility = View.VISIBLE
                checkData()
            }
        }
    }

    private fun checkData() {
        with(binding) {
            buttonSignup.isEnabled =
                name.isNotEmpty() && birth.isNotEmpty() && phoneNumber.isNotEmpty() && dwelling.isNotEmpty() && password.isNotEmpty() && passwordCheck.isNotEmpty() && button1.isChecked && button2.isChecked && button3.isChecked && button4.isChecked
        }
    }
}