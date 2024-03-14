package com.example.teplica.data
import android.os.Parcel
import android.os.Parcelable

data class settings(
    var temp: Int,
    var grass: Int,
    var cooling: Int, //bool
    var light: Int, //bool
    var watering: Int, //bool
    var auto_mode: Int //bool
)