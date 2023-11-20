package com.guji.welfare.guji_welfare_e_android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel

abstract class BaseDialogFragment<B : ViewDataBinding, VM : ViewModel>(
    @LayoutRes private val layoutRes: Int
): DialogFragment() {

    protected lateinit var binding: B
    protected lateinit var mViewModel: VM
    protected abstract val viewModel: VM


    protected abstract fun start()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareDataBinding()
//        (activity as? MainActivity)?.setNavVisible(hasBottomNavigation)
        start()
    }

    private fun prepareDataBinding() {
        mViewModel = if (::mViewModel.isInitialized) mViewModel else viewModel
//        binding.setVariable(BR.vm, viewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::binding.isInitialized) binding.unbind()
    }
}