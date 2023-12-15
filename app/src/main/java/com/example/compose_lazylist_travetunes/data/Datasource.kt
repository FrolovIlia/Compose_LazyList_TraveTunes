package com.example.compose_lazylist_travetunes.data

import com.example.compose_lazylist_travetunes.R
import com.example.compose_lazylist_travetunes.model.InterestingPoint


class Datasource() {
    fun loadAffirmations(): List<InterestingPoint> {
        return listOf<InterestingPoint>(
            InterestingPoint(R.string.iv_description1, R.drawable.iv_1),
            InterestingPoint(R.string.iv_description2, R.drawable.iv_2),
            InterestingPoint(R.string.iv_description3, R.drawable.iv_3),
            InterestingPoint(R.string.iv_description4, R.drawable.iv_4),
            InterestingPoint(R.string.iv_description5, R.drawable.iv_5),
            InterestingPoint(R.string.iv_description6, R.drawable.iv_6),
            InterestingPoint(R.string.iv_description7, R.drawable.iv_7),
            InterestingPoint(R.string.iv_description8, R.drawable.iv_8),
            InterestingPoint(R.string.iv_description9, R.drawable.iv_9),
            InterestingPoint(R.string.iv_description10, R.drawable.iv_10),
            InterestingPoint(R.string.iv_description11, R.drawable.iv_12)

        )
    }
}