package io.travel_tunes.utils.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyOuterHorizontalSpaceItemDecoration(
    private val startSpaceSize: Int = 0,
    private val endSpaceSize: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.adapter != null && parent.getChildAdapterPosition(view) == 0) {
            outRect.left = startSpaceSize
        }
        if (parent.adapter != null && parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.right = endSpaceSize
        }
    }
}