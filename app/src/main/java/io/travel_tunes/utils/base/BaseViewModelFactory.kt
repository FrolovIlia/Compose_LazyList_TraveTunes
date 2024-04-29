package io.travel_tunes.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelFactory<TYPE : ViewModel> : ViewModelProvider.Factory {
	protected abstract fun getViewModel(): TYPE

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		val viewModel = getViewModel()
		if (modelClass.isAssignableFrom(viewModel.javaClass)) {
			@Suppress("UNCHECKED_CAST") return viewModel as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}