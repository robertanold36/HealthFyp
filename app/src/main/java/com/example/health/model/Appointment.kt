package com.example.health.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Appointment(
    val dayName: String, val time: String, val status: String,val appointmentId:String
    ):Parcelable
