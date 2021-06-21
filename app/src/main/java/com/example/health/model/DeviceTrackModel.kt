package com.example.health.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_track")
data class DeviceTrackModel(
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "description") val desc: String,
    @ColumnInfo(name = "sugar_concentration") val sugarConcentration: Double,
    @ColumnInfo(name = "meals") val timeMeals:String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
