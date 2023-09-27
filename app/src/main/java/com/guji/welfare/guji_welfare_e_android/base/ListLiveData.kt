package com.guji.welfare.guji_welfare_e_android.base

import android.util.Log
import androidx.lifecycle.MutableLiveData


class ListLiveData<T> : MutableLiveData<MutableList<T>>() {
    fun add(item: T) {
        Log.d("value",value.toString())
        value?.add(item)
    }

    fun addAll(items: List<T>) {
        val tempList = value ?: mutableListOf()
        tempList.addAll(items)
        value = tempList
    }

    fun remove(item: T) {
        val tempList = value ?: mutableListOf()
        tempList.remove(item)
        value = tempList
    }

    fun clear() {
        value?.clear()
    }
}