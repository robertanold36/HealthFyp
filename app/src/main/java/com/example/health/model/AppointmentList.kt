package com.example.health.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class AppointmentList: ArrayList<Appointment>(), Parcelable
