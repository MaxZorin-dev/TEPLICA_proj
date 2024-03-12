package com.example.teplica.data
import android.os.Parcel
import android.os.Parcelable

data class settings(
    val temp: Int,
    val grass: Int,
    val cooling: Int, //bool
    val light: Int, //bool
    val watering: Int //bool
)