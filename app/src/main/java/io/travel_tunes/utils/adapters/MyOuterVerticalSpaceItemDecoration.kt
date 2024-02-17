package io.travel_tunes.utils.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class MyOuterVerticalSpaceItemDecoration(
    private val topSpaceSize: Int = 0,
    private val bottomSpaceSize: Int = 0
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.adapter != null && parent.getChildAdapterPosition(view) == 0) {
            outRect.top = topSpaceSize
        }
        if (parent.adapter != null && parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.bottom = bottomSpaceSize
        }
    }
}