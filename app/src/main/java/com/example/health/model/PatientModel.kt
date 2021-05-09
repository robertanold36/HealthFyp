package com.example.health.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PatientModel(
    var name: String = "",
    var email: String = "",
    var disease: String = "",
    var hospitalName: String = "",
    var date: String = "",
    var doctorId: String =""
):Parcelable
