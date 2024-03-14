package com.example.teplica


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.teplica.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ws:WebSocket
    private lateinit var chart: LineChart


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chart = binding.lineChart

        drawLineChart()

        Log.d("mLog","1212")


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