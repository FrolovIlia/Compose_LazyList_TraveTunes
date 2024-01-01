package com.example.compose_lazylist_travetunes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InterestingPoint(
    @StringRes @PrimaryKey(autoGenerate = true) val titleID: Int,
    @StringRes val descriptionID: Int,
    @DrawableRes val pictureID: Int
)