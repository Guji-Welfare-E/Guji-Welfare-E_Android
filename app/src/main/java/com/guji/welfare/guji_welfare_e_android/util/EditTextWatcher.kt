package com.guji.welfare.guji_welfare_e_android.util

import android.text.Editable
import android.text.TextWatcher

class EditTextWatcher: TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //텍스트가 변경 되기면 호출

    }

    override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) {
        //텍스트가 변경될 때 호출
    }

    override fun afterTextChanged(p0: Editable?) {
        //텍스트가 변경된 후에 호출
    }
}