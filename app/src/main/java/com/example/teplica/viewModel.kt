package com.example.teplica

import android.util.Log
import android.widget.CompoundButton
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams


class MainViewModel: ViewModel() {

    val isChecked: MutableLiveData<Boolean> = MutableLiveData()

    fun executeOnStatusChanged(switch: CompoundButton, isChecked: Boolean) {
        Log.d("mLog","$isChecked")
    }


    val liveData = MutableLiveData<Boolean>().apply {
        value = false
    }
    fun onSeeking(seekBar: IndicatorSeekBar?, seekParams: SeekParams) {
        Log.d("mLog", "435345345")
        Log.d("mLog", "onSeeking: " + seekBar?.progress)
    }

    fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {
        Log.d("mLog", "onSeeking: " + seekBar.progress)
    }

    fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {
        Log.d("mLog", "onSeeking: " + seekBar.progress)
    }

}

