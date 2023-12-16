package com.example.compose_lazylist_travetunes.data

import com.example.compose_lazylist_travetunes.R
import com.example.compose_lazylist_travetunes.model.InterestingPoint


class Datasource() {
    fun loadInterestingPoint(): List<InterestingPoint> {
        return listOf<InterestingPoint>(
            InterestingPoint(R.string.spb_title1, R.string.spb_description1, R.drawable.spb_1),
            InterestingPoint(R.string.spb_title2, R.string.spb_description2, R.drawable.spb_2),
            InterestingPoint(R.string.spb_title3, R.string.spb_description3, R.drawable.spb_3),
            InterestingPoint(R.string.spb_title4, R.string.spb_description4, R.drawable.spb_4),
        )
    }
}