package com.example.compose_lazylist_travetunes.data

import com.example.compose_lazylist_travetunes.R
import com.example.compose_lazylist_travetunes.model.InterestingPointEntity
object DataGenerator {
    fun getDefaultPointsList() = listOf(
        InterestingPointEntity(R.string.spb_title1, R.string.spb_description1, R.drawable.spb_1, id = 1),
        InterestingPointEntity(R.string.spb_title2, R.string.spb_description2, R.drawable.spb_2, id = 2),
        InterestingPointEntity(R.string.spb_title3, R.string.spb_description3, R.drawable.spb_3, id = 3),
        InterestingPointEntity(R.string.spb_title4, R.string.spb_description4, R.drawable.spb_4, id = 4),
    )
}