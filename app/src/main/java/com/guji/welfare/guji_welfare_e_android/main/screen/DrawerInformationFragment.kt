package com.guji.welfare.guji_welfare_e_android.main.screen

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseFragment
import com.guji.welfare.guji_welfare_e_android.databinding.FragmentDrawerInformationBinding
import com.guji.welfare.guji_welfare_e_android.main.adapter.GuardianInformationListAdapter
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import com.guji.welfare.guji_welfare_e_android.main.viewmodel.InformationViewModel

class DrawerInformationFragment :
    BaseFragment<FragmentDrawerInformationBinding, InformationViewModel>(
        R.layout.fragment_drawer_information
    ) {
    override val viewModel: InformationViewModel by viewModels()

    override fun start() {
        val context = requireContext()
        val guardiaInformationAdapter = GuardianInformationListAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = guardiaInformationAdapter

        binding.buttonAdd.setOnClickListener {
            val explanationData = mutableListOf(
                GuardianInformationData(
                    "황주완",
                    "아들",
                    "010-1234-5678"
                ),
                GuardianInformationData(
                    "안우진",
                    "딸",
                    "010-1234-5678"
                )
            )
            guardiaInformationAdapter.submitList(explanationData)
        }
    }
}