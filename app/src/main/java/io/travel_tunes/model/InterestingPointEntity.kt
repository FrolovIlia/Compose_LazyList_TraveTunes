package io.travel_tunes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.travel_tunes.utils.Constants

@Entity(tableName = Constants.POINT_TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class InterestingPointEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") @StringRes val title: Int,
    @ColumnInfo(name = "description") @StringRes val description: Int,
    @ColumnInfo(name = "picture") @DrawableRes val picture: Int,
    @ColumnInfo(name = "city_code_name") val cityCodeName: String
)