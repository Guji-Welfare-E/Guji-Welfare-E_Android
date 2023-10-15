package com.guji.welfare.guji_welfare_e_android.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guji.welfare.guji_welfare_e_android.R
import com.guji.welfare.guji_welfare_e_android.base.BaseListAdapter
import com.guji.welfare.guji_welfare_e_android.databinding.ItemDiseaseDisorderInformationBinding
import com.guji.welfare.guji_welfare_e_android.main.adapter.data.DiseaseDisorderInformationData
import com.guji.welfare.guji_welfare_e_android.util.OnSingleClickListener

class DiseaseDisorderInformationListAdapter: BaseListAdapter<DiseaseDisorderInformationData, ItemDiseaseDisorderInformationBinding>(
    R.layout.item_disease_disorder_information) {
    override fun action(
        data: DiseaseDisorderInformationData,
        binding: ItemDiseaseDisorderInformationBinding
    ) {
        with(binding){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            ItemDiseaseDisorderInformationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        with(holder) {
            itemView.setOnClickListener(OnSingleClickListener {
                itemClickListener.onClick(it, position)
            })
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)

    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}