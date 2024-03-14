package com.example.teplica.data

import androidx.databinding.BindingAdapter
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams


//class IndicatorSeekBarBindingAdapter {
//    @BindingAdapter(
//        value = ["app:onSeeking", "app:onStartTrackingTouch", "app:onStopTrackingTouch"],
//        requireAll = false
//    )
//    fun setOnSeekBarChangeListener(
//        seekBar: IndicatorSeekBar,
//        seeking: OnSeeking?,
//        start: OnStartTrackingTouch?,
//        stop: OnStopTrackingTouch?
//    ) {
//        if (seeking != null || start != null || stop != null) {
//            seekBar.onSeekChangeListener = object : OnSeekChangeListener {
//                override fun onSeeking(seekParams: SeekParams) {
//                    seeking?.onSeeking(seekBar, seekParams)
//                }
//
//                override fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {
//                    start?.onStartTrackingTouch(seekBar)
//                }
//
//                override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {
//                    stop?.onStopTrackingTouch(seekBar)
//                }
//            }
//        }
//    }
//
//    interface OnSeeking {
//        fun onSeeking(seekBar: IndicatorSeekBar?, seekParams: SeekParams?)
//    }
//
//    interface OnStartTrackingTouch {
//        fun onStartTrackingTouch(seekBar: IndicatorSeekBar?)
//    }
//
//    interface OnStopTrackingTouch {
//        fun onStopTrackingTouch(seekBar: IndicatorSeekBar?)
//    }
//}