package com.example.compose_lazylist_travetunes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose_lazylist_travetunes.model.InterestingPoint
import com.example.compose_lazylist_travetunes.persistence.InterestingPointDao

class InterestingPointViewModel(private val db: InterestingPointDao): ViewModel() {

    suspend fun getNote() : LiveData<List<InterestingPoint>> {
        return db.getAllInterestingPoints()
    }
}


class NotesViewModelFactory(
    private val db: InterestingPointDao,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  InterestingPointViewModel(
            db = db,
        ) as T
    }

}