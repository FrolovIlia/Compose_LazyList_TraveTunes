package io.travel_tunes.utils.base

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.travel_tunes.R

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

	private var bottomSheet: FrameLayout? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
	}

	fun setInitExpandedState(viewRoot: ViewGroup, skipCollapsed: Boolean = false) {
		viewRoot.viewTreeObserver.addOnGlobalLayoutListener {
			bottomSheet = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
			bottomSheet?.let {
				val behavior = BottomSheetBehavior.from(it)
				behavior.state = BottomSheetBehavior.STATE_EXPANDED
				behavior.skipCollapsed = skipCollapsed
			}
		}
	}
}