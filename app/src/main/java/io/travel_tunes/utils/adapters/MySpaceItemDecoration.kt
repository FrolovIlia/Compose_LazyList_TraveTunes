package io.travel_tunes.utils.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MySpaceItemDecoration(val spaceSize: Int = 0, val orientation: Orientation = Orientation.VERTICAL) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {

        when (orientation) {
            Orientation.VERTICAL -> {
                if (parent.adapter != null && parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
                    outRect.bottom = spaceSize
                }
            }
            else -> {
                if (parent.adapter != null && parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
                    outRect.right = spaceSize
                }
            }
        }

    }

    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }
}