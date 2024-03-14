package com.example.teplica

import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel: ViewModel() {

    var progressTemp = MutableLiveData<Int>().apply {
        value = 0

    }

    fun onValueChanged(progresValue: SeekBar) {
        Log.d("mLog","${progresValue.progress}")
    }

//    fun onSeekBarChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
////        text.set("Your progress: $progress")
//
//    }

}