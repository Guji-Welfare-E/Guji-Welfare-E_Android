package com.guji.welfare.guji_welfare_e_android.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseListAdapter
import com.guji.welfare.guji_welfare_e_android.data.dto.user.Guardian
import com.guji.welfare.guji_welfare_e_android.databinding.ItemGuardiaInformationBinding
import com.guji.welfare.guji_welfare_e_android.util.OnSingleClickListener

class GuardianInformationListAdapter :
    BaseListAdapter<Guardian, ItemGuardiaInformationBinding>(
        R.layout.item_guardia_information
    ) {
    override fun action(data: Guardian, binding: ItemGuardiaInformationBinding) {
        with(binding) {
            name.text = data.name
            phoneNumber.text = data.telephoneNum
            relationship.text = data.info
        }
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

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        with(holder) {
            binding.callButton.setOnClickListener(OnSingleClickListener {
                itemClickListener.onClickCall(it, position)
            })
            binding.layoutChangeInformation.setOnClickListener(OnSingleClickListener {
                itemClickListener.onClick(it, position)
            })
        }
        return holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onClickCall(v: View, position: Int)

        fun onClick(v: View, position: Int)

    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener

}