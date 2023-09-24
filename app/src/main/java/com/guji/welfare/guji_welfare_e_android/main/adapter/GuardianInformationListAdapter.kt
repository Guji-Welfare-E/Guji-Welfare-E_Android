package com.guji.welfare.guji_welfare_e_android.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseListAdapter
import com.guji.welfare.guji_welfare_e_android.databinding.ItemGuardiaInformationBinding
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.GuardianInformationData
import com.guji.welfare.guji_welfare_e_android.main.screen.MainActivity

class GuardianInformationListAdapter :
    BaseListAdapter<GuardianInformationData, ItemGuardiaInformationBinding>(
        R.layout.item_guardia_information
    ), BaseListAdapter.OnItemClickListener  {
    override fun action(data: GuardianInformationData, binding: ItemGuardiaInformationBinding) {
        binding.name.text = data.name
        binding.phoneNumber.text = data.phoneNumber
        binding.relationship.text = data.relationship
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            ItemGuardiaInformationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onClick(v: View, position: Int) {

    }
}