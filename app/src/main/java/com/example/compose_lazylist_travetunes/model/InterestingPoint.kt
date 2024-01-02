package com.example.compose_lazylist_travetunes.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.compose_lazylist_travetunes.Constants

@Entity(tableName = Constants.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class InterestingPoint(
    @ColumnInfo(name = "title")
    val title: Int,
    @ColumnInfo(name = "description")
    val description: Int,
    @ColumnInfo(name = "picture")
    @DrawableRes
    val picture: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)