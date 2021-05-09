package com.example.health.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppointmentRequest(
    var dayName: String="",
    var time: String="",
    var appointmentId: String="",
    var name: String="",
    var uid:String=""

):Parcelable
