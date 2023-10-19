package com.guji.welfare.guji_welfare_e_android.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseListAdapter
import com.guji.welfare.guji_welfare_e_android.data.dto.user.DiseaseDisorder
import com.guji.welfare.guji_welfare_e_android.databinding.ItemDiseaseDisorderInformationBinding
import com.guji.welfare.guji_welfare_e_android.util.OnSingleClickListener

class DiseaseDisorderInformationListAdapter: BaseListAdapter<DiseaseDisorder, ItemDiseaseDisorderInformationBinding>(
    R.layout.item_disease_disorder_information) {
    override fun action(
        data: DiseaseDisorder,
        binding: ItemDiseaseDisorderInformationBinding
    ) {
        with(binding){
            textDiseaseDisorder.text = data.name
            val date = data.createTime
            val datePart = date.substring(0, 10)
            textDate.text = datePart
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        Log.d("상태","onCreateViewHolder")
        return BaseViewHolder(
            ItemDiseaseDisorderInformationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        Log.d("상태","onBindViewHolder")
        with(holder) {
            itemView.setOnClickListener(OnSingleClickListener {
                itemClickListener.onClickDieaseInformation(it, position)
            })
        }
        return holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onClickDieaseInformation(v: View, position: Int)

    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}