package com.example.compose_lazylist_travetunes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.compose_lazylist_travetunes.Constants

@Entity(tableName = Constants.CITY_TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "name") @StringRes val name: Int,
    @ColumnInfo(name = "description") @StringRes val description: Int,
    @ColumnInfo(name = "picture") @DrawableRes val picture: Int,
    @ColumnInfo(name = "code_name") val codeName: String
)