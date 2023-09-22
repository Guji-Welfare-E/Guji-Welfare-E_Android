package com.guji.welfare.guji_welfare_e_android.main.screen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.activity.viewModels
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseActivity
import com.guji.welfare.guji_welfare_e_android.databinding.ActivityMainBinding
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.MainViewModel


class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel: MainViewModel by viewModels()

    override fun start() {

    }

    override fun onResume() {
        super.onResume()
    }

    fun setDialog(dialogLayout: Int){
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogLayout)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}