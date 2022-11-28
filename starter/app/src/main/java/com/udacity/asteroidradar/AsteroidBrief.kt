package com.udacity.asteroidradar

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AsteroidBrief(
    val id: String, val name: String, val date: String,val  hazard_status: Boolean
) : Parcelable