package com.example.compose_lazylist_travetunes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class InterestingPoint(
    @StringRes val titleID: Int,
    @StringRes val descriptionID: Int,
    @DrawableRes val pictureID: Int
)