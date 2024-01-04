package com.example.compose_lazylist_travetunes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compose_lazylist_travetunes.R
import com.example.compose_lazylist_travetunes.model.InterestingPoint


class Datasource {
    private var listOfPoints = MutableLiveData<List<InterestingPoint>>()

    init {
        listOfPoints.postValue(
            listOf(
                InterestingPoint(R.string.spb_title1, R.string.spb_description1, R.drawable.spb_1, id = 2),
                InterestingPoint(R.string.spb_title2, R.string.spb_description2, R.drawable.spb_2, id = 3),
                InterestingPoint(R.string.spb_title3, R.string.spb_description3, R.drawable.spb_3, id = 4),
                InterestingPoint(R.string.spb_title4, R.string.spb_description4, R.drawable.spb_4, id = 5),
            )
        )
    }

    fun loadInterestingPoints(): LiveData<List<InterestingPoint>> {
        return listOfPoints
    }

    fun addInterestingPoint(point: InterestingPoint) {
        val currentPoints = listOfPoints.value ?: emptyList()
        val resultPoints = currentPoints.plus(point)
        listOfPoints.postValue(resultPoints)
    }
}