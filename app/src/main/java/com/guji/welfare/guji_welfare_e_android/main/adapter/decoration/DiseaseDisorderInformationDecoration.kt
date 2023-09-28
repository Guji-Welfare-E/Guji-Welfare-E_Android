package com.guji.welfare.guji_welfare_e_android.main.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DiseaseDisorderInformationDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val count = state.itemCount
        when (position) {
            0 -> outRect.bottom = 15
            count - 1 -> outRect.top = 15
            else -> {
                outRect.top = 15
                outRect.bottom = 15
            }
        }
    }
}