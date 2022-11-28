package com.udacity.asteroidradar

import com.squareup.moshi.Json
import com.udacity.asteroidradar.db.Entities

data class PictureOfDay(
    @Json(name = "media_type") val mediaType: String, val title: String,
    val url: String
)
fun PictureOfDay.toPictureOfDayEntity(): Entities.PictureOfDayEntity {
    return Entities.PictureOfDayEntity("1", mediaType, title, url)
}
