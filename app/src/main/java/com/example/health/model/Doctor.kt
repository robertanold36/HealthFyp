package com.example.health.model

import java.io.Serializable

data class Doctor(
    var name: String="",
    var hospital: String="",
    var phoneNumber: String="",
    var professional: String="",
    var doctorId:String=""
):Serializable
