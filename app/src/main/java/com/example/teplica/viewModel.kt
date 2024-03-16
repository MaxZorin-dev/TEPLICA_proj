package com.example.teplica

import android.util.Log
import android.widget.CompoundButton
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class MainViewModel: ViewModel() {

    val isChecked: MutableLiveData<Boolean> = MutableLiveData()

    fun executeOnStatusChanged(switch: CompoundButton, isChecked: Boolean) {
        Log.d("mLog","$isChecked")
    }


    val liveData = MutableLiveData<Boolean>().apply {
        value = false
    }


}

