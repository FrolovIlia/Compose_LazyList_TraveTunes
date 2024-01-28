package io.travel_tunes.data

import io.travel_tunes.R
import io.travel_tunes.model.CityEntity
import io.travel_tunes.model.InterestingPointEntity
object DataGenerator {
    private const val MoscowCodeName = "msk"
    private const val SaintPeterCodeName = "spb"
    fun getDefaultPointsList() = listOf(
        InterestingPointEntity(
            id = 1,
            title = R.string.spb_point_title1,
            description = R.string.spb_point_description1,
            picture = R.drawable.spb_1,
            cityCodeName = SaintPeterCodeName),
        InterestingPointEntity(
            id = 2,
            title = R.string.spb_point_title2,
            description = R.string.spb_point_description2,
            picture = R.drawable.spb_2,
            cityCodeName = SaintPeterCodeName),
        InterestingPointEntity(
            id = 3,
            title = R.string.spb_point_title3,
            description = R.string.spb_point_description3,
            picture = R.drawable.spb_3,
            cityCodeName = SaintPeterCodeName),
        InterestingPointEntity(
            id = 4,
            title = R.string.spb_point_title4,
            description = R.string.spb_point_description4,
            picture = R.drawable.spb_4,
            cityCodeName = SaintPeterCodeName),
        InterestingPointEntity(
            id = 11,
            title = R.string.msk_point_title1,
            description = R.string.msk_point_description1,
            picture = R.drawable.msk_1,
            cityCodeName = MoscowCodeName),
        InterestingPointEntity(
            id = 12,
            title = R.string.msk_point_title2,
            description = R.string.msk_point_description2,
            picture = R.drawable.msk_2,
            cityCodeName = MoscowCodeName),
        InterestingPointEntity(
            id = 13,
            title = R.string.msk_point_title3,
            description = R.string.msk_point_description3,
            picture = R.drawable.msk_3,
            cityCodeName = MoscowCodeName),
        InterestingPointEntity(
            id = 14,
            title = R.string.msk_point_title4,
            description = R.string.msk_point_description4,
            picture = R.drawable.msk_4,
            cityCodeName = MoscowCodeName)
    )

    fun getDefaultCities() = listOf(
        CityEntity(
            id = 1,
            name = R.string.msk_name,
            description = R.string.msk_description,
            codeName = MoscowCodeName,
            picture = R.drawable.msk_city
        ),
        CityEntity(
            id = 2,
            name = R.string.spb_name,
            description = R.string.spb_description,
            codeName = SaintPeterCodeName,
            picture = R.drawable.spb_city
        )
    )
}