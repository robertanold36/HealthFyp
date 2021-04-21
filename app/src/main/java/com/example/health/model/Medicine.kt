package com.example.health.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "medicine")
data class Medicine(
    @ColumnInfo(name = "medicine_name") val name: String,
    @ColumnInfo(name = "tablets_quantity") val tabletsQuantity: Int,
    @ColumnInfo(name = "tablets_interval") val tabletsInterval: Int,
    @ColumnInfo(name = "doze") val doze: Int,
    @ColumnInfo(name = "doze_time") val dozeTime: String,
    @ColumnInfo(name = "date") val date: String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}
