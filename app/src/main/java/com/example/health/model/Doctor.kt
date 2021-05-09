package com.example.health.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Doctor(
    var name: String="",
    var hospital: String="",
    var phoneNumber: String="",
    var professional: String="",
    var doctorId:String=""
):Parcelable
