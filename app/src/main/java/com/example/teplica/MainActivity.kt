package com.example.teplica


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.teplica.data.settings
import com.example.teplica.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var ws:WebSocket
    private lateinit var chart: LineChart
    private val defaultSettings = settings(24,90,0,0,0, 0)


    fun connect(){
        val client =  OkHttpClient()
        val base_uri = "ws://192.168.112.22:8080"
        val id = 123

        val request: Request = Request
            .Builder()
            .url("${base_uri}/ws/mob_${id}")
            .build()

        val listener = PieSocketListener()
        ws= client.newWebSocket(request, listener)
    }

    fun setDefaultSettings(){
        binding.temptext.text = defaultSettings.temp.toString()
        binding.seektemp.setProgress(defaultSettings.temp)

        binding.humText.text =defaultSettings.grass.toString()
        binding.seekhum.setProgress(defaultSettings.grass)

        binding.switchFan.setChecked(defaultSettings.cooling.toString().toBoolean())
        binding.switchLight.setChecked(defaultSettings.light.toString().toBoolean())
        binding.switchPump.setChecked(defaultSettings.watering.toString().toBoolean())
    }

    fun setPeopleSettings(settings: settings){
        Log.d("mLog","${settings}")
        binding.temptext.text = settings.temp.toString()
        binding.seektemp.setProgress(settings.temp)

        binding.humText.text =settings.grass.toString()
        binding.seekhum.setProgress(settings.grass)

        binding.switchFan.setChecked(settings.cooling.toString().toBoolean())
        binding.switchLight.setChecked(settings.light.toString().toBoolean())
        binding.switchPump.setChecked(settings.watering.toString().toBoolean())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chart = binding.lineChart
        binding.switchPump.setChecked(true)

        val settingsPeople = settings(15,0,0,0,0,0)

        binding.seekhum.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                binding.humText.text = seek.progress.toString()
                settingsPeople.grass = seek.progress
            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {

            }
        })

        binding.seektemp.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                binding.temptext.text = seek.progress.toString()
                settingsPeople.temp = seek.progress
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })

        binding.switchAuto.setOnClickListener {
            if (binding.switchAuto.isChecked()) {
                settingsPeople.auto_mode = 1
                setDefaultSettings()
            } else {
//                settingsPeople.auto_mode = 0
                setPeopleSettings(settingsPeople)
            }
        }

        binding.switchFan.setOnClickListener {
            if (binding.switchFan.isChecked()) {
                settingsPeople.cooling = 1
            } else {
                settingsPeople.cooling = 0
                Log.d("mLog","${settingsPeople}")
            }
        }

        binding.switchLight.setOnClickListener {
            if (binding.switchLight.isChecked()) {
                settingsPeople.light = 1
                Log.d("mLog","on")
            } else {
                settingsPeople.light = 0
                Log.d("mLog","off")
            }
        }

        binding.switchPump.setOnClickListener {
            if (binding.switchPump.isChecked()) {
                settingsPeople.watering = 1
                Log.d("mLog","${settingsPeople}")
            } else {
                settingsPeople.watering = 0

            }
        }

//
//        humBar.setOnSeekChangeListener(object : OnSeekChangeListener {
//            override fun onSeeking(seekParams: SeekParams) {
//
//            }
//
//            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {
////                onStartTrackingTouch(seekBar)
//            }
//
//            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {
////                onStopTrackingTouch(seekBar)
//            }
//        })

        drawLineChart()




//        val progress = Progres()
//        binding.setProgress(progress)
//
//        progress.porgress.set(21)

//        binding.humSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
//                binding.humText.text = seek.progress.toString()
//            }
//
//            override fun onStartTrackingTouch(seek: SeekBar) {
//
//            }
//
//            override fun onStopTrackingTouch(seek: SeekBar) {
//
//            }
//        })



//        val  button_send: Button = findViewById(R.id.button_send)
//        val  button_connect: Button = findViewById(R.id.button_connect)
//
//
//
//        button_connect.setOnClickListener {
//            connect()
//        }
//
//        val jsonData = Klaxon().toJsonString(webInfo("server_settings", settings(1,1,0,1, 0, 1)))
//
//        button_send.setOnClickListener{
//
//            ws.send(jsonData)
//
//
//        }



    }
//    private fun drawLineChart() {
//        val lineChart = findViewById<LineChart>(R.id.lineChart)
//        val lineEntries: List<Entry> = getDataSet()
//        val lineDataSet = LineDataSet(lineEntries, "Work")
//        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
//        lineDataSet.lineWidth = 2f
//        lineDataSet.setDrawValues(false)
//        lineDataSet.color = Color.CYAN
//        lineDataSet.circleRadius = 6f
//        lineDataSet.circleHoleRadius = 3f
//        lineDataSet.setDrawCircles(false)
//        lineDataSet.setDrawHighlightIndicators(true)
//        lineDataSet.isHighlightEnabled = true
//        lineDataSet.highLightColor = Color.CYAN
//        lineDataSet.valueTextSize = 12f
//        lineDataSet.valueTextColor = Color.DKGRAY
//        lineDataSet.mode = LineDataSet.Mode.STEPPED
//
//        val lineData = LineData(lineDataSet)
//        lineChart.description.textSize = 12f
//        lineChart.description.isEnabled = false
//        lineChart.animateY(1000)
//        lineChart.data = lineData
//
//        // Setup X Axis
//        val xAxis = lineChart.xAxis
//        xAxis.position = XAxis.XAxisPosition.TOP
//        xAxis.isGranularityEnabled = true
//        xAxis.granularity = 1.0f
//        xAxis.xOffset = 1f
//        xAxis.labelCount = 25
//        xAxis.axisMinimum = 0f
//        xAxis.axisMaximum = 24f
//
//        // Setup Y Axis
//        val yAxis = lineChart.axisLeft
//        yAxis.axisMinimum = 0f
//        yAxis.axisMaximum = 3f
//        yAxis.granularity = 1f
//
//        val yAxisLabel = ArrayList<String>()
//        yAxisLabel.add(" ")
//        yAxisLabel.add("Rest")
//        yAxisLabel.add("Work")
//        yAxisLabel.add("2-up")
//
//        lineChart.axisLeft.setCenterAxisLabels(true)
//        lineChart.axisLeft.valueFormatter = object : ValueFormatter() {
//            override fun getAxisLabel(value: Float, axis: AxisBase): String {
//                if (value == -1f || value >= yAxisLabel.size) return ""
//                return yAxisLabel[value.toInt()]
//            }
//        }
//
//        lineChart.axisRight.isEnabled = false
//        lineChart.invalidate()
//    }



    fun drawLineChart() {

        // Массив координат точек
        val entriesFirst = ArrayList<Entry>()
        entriesFirst.add(Entry(1f, 5f))
        entriesFirst.add(Entry(2f, 2f))
        entriesFirst.add(Entry(3f, 1f))
        entriesFirst.add(Entry(4f, -3f))
        entriesFirst.add(Entry(5f, 4f))
        entriesFirst.add(Entry(6f, 1f))

        val datasetFirst = LineDataSet(entriesFirst, "График первый")

        datasetFirst.setDrawFilled(true)

        val entriesSecond = ArrayList<Entry>()
        entriesSecond.add(Entry(0.5f, 0f))
        entriesSecond.add(Entry(2.5f, 2f))
        entriesSecond.add(Entry(3.5f, 1f))
        entriesSecond.add(Entry(3.6f, 2f))
        entriesSecond.add(Entry(4f, 0.5f))
        entriesSecond.add(Entry(5.1f, -0.5f))


        val datasetSecond = LineDataSet(entriesSecond, "График второй")

        datasetSecond.color = Color.GREEN

        datasetSecond.mode = LineDataSet.Mode.CUBIC_BEZIER


        val dataSets: ArrayList<ILineDataSet?> = ArrayList()
        dataSets.add(datasetFirst)
        dataSets.add(datasetSecond)

        val data = LineData(dataSets)

        chart.setData(data)
        chart.animateY(500)

    }

}

