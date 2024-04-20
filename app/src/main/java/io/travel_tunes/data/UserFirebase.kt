package io.travel_tunes.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserFirebase(
    val userId: String,
    val userRoutes: List<String> = emptyList()
)