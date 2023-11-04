package com.guji.welfare.guji_welfare_e_android.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.DiseaseViewModel
import com.guji.welfare.guji_welfare_e_android.dialog.viewmodel.GuardianViewModel

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutRes: Int
) : AppCompatActivity() {

    protected lateinit var binding: B
    private lateinit var mViewModel: VM
    protected abstract val viewModel: VM

    protected lateinit var diseaseViewModel: DiseaseViewModel
    protected lateinit var guardianViewModel: GuardianViewModel


    protected open fun preLoad() {}
    protected abstract fun start()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preLoad()
        prepareDataBinding()
        start()
    }

    private fun prepareDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutRes)
        mViewModel = if (::mViewModel.isInitialized) mViewModel else viewModel
        diseaseViewModel = ViewModelProvider(this)[DiseaseViewModel::class.java]
        guardianViewModel = ViewModelProvider(this)[GuardianViewModel::class.java]
//        binding.setVariable(BR.vm, viewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::binding.isInitialized) binding.unbind()
    }
}