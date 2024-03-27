package com.example.teplica


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.beust.klaxon.Klaxon
import com.example.teplica.data.settings
import com.example.teplica.data.state
import com.example.teplica.data.webInfo
import com.example.teplica.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ws: WebSocket
    private lateinit var chart: LineChart
    private var savesettings = settings(15, 0, 0, 0, 0, 0)
    private val defaultSettings = settings(26, 70, 0, 0, 0, 1)
    private val settingsPeople = settings(15, 0, 0, 0, 0, 0)
    private val stateUI = state(0,0,0,0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chart = binding.lineChart

        viewLogic()
        connect()
        webThread.start()
    }

    val webThread = Thread{
        try {
            while (true) {
                if (settingsPeople != savesettings) {
                    savesettings = settingsPeople.copy()
                    Log.d("mLog","$savesettings")
                    val jsonData = Klaxon().toJsonString(webInfo("server_settings", settingsPeople))
                    ws.send(jsonData)
                }
                Thread.sleep(5000)
            }
        }catch (e:Exception){
            Log.d("mLog","$e")
        }
    }

    private fun intToBoolean(value: Int): Boolean = if ( value == 1) true else false

    fun viewLogic(){
        setDefaultSettings()
        binding.switchAuto.isChecked = true

        binding.seekhum.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                if (settingsPeople.auto_mode == 0) {
                    binding.humText.text = seek.progress.toString()
                    settingsPeople.grass = seek.progress
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })

        binding.seektemp.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                if (settingsPeople.auto_mode == 0) {
                    binding.temptext.text = seek.progress.toString()
                    settingsPeople.temp = seek.progress
                }
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
                settingsPeople.auto_mode = 0
                setPeopleSettings(settingsPeople)
            }
        }

        binding.switchFan.setOnClickListener {
            if (settingsPeople.auto_mode == 0) {
                if (binding.switchFan.isChecked()) {
                    settingsPeople.cooling = 1

                } else {
                    settingsPeople.cooling = 0

                }
            }
        }

        binding.switchLight.setOnClickListener {
            if (settingsPeople.auto_mode == 0) {
                if (binding.switchLight.isChecked()) {
                    settingsPeople.light = 1

                } else {

                    settingsPeople.light = 0

                }
            }
        }

        binding.switchPump.setOnClickListener {
            if (settingsPeople.auto_mode == 0) {
                if (binding.switchPump.isChecked()) {
                    settingsPeople.watering = 1

                } else {
                    settingsPeople.watering = 0

                }
            }
        }

        drawLineChart()
    }

    fun vison(param : Boolean){
        binding.visonParam.isVisible = param
        binding.visonTools.isVisible = param
        binding.visonToolsText.isVisible = param
        binding.visonParamText.isVisible = param
    }

    fun connect(){
        val client =  OkHttpClient()
        val base_uri = "ws://192.168.1.78:8080"
        val id = 123

        val request: Request = Request
            .Builder()
            .url("${base_uri}/ws/mob_${id}")
            .build()

        val listener = PieSocketListener(this, {x -> setWebSettings(x)} , {x -> setState(x)} )
        listener.setData(stateUI, settingsPeople)
        ws = client.newWebSocket(request, listener)

    }

    fun setDefaultSettings(){
        binding.temptext.text = defaultSettings.temp.toString()
        binding.seektemp.setProgress(defaultSettings.temp)
        binding.seektemp.isEnabled = false

        binding.humText.text = defaultSettings.grass.toString()
        binding.seekhum.setProgress(defaultSettings.grass)
        binding.seekhum.isEnabled = false

        binding.switchFan.setChecked(intToBoolean(defaultSettings.cooling))
        binding.switchLight.setChecked(intToBoolean(defaultSettings.light))
        binding.switchPump.setChecked(intToBoolean(defaultSettings.watering))

        binding.switchFan.isEnabled = false
        binding.switchLight.isEnabled = false
        binding.switchPump.isEnabled = false
    }

    fun setPeopleSettings(settings: settings){
        Log.d("mLog","${settings}")

        binding.seektemp.isEnabled = true
        binding.temptext.text = settings.temp.toString()
        binding.seektemp.setProgress(settings.temp)

        binding.seekhum.isEnabled = true
        binding.humText.text = settings.grass.toString()
        binding.seekhum.setProgress(settings.grass)

        binding.switchFan.isEnabled = true
        binding.switchLight.isEnabled = true
        binding.switchPump.isEnabled = true

        binding.switchFan.setChecked(intToBoolean(settings.cooling))
        binding.switchLight.setChecked(intToBoolean(settings.light))
        binding.switchPump.setChecked(intToBoolean(settings.watering))
    }
    fun setWebSettings(settings: settings){
        if (settings.auto_mode == 1){
            binding.switchAuto.isChecked = true
            setDefaultSettings()
        }else{
            binding.switchAuto.isChecked = false
            setPeopleSettings(settings)
        }

    }

    fun setState(state: state){
        binding.progressWater.progress = state.water_level
        binding.topCardTemp.text = state.temp.toFloat().toString()
        binding.progressHum.progress = state.humidity_air
    }

    fun drawLineChart() {
        val entriesFirst = ArrayList<Entry>()
        // Массив координат точек
        entriesFirst.add(Entry(1f, 23f))
        entriesFirst.add(Entry(2f, 25f))
        entriesFirst.add(Entry(3f, 26f))
        entriesFirst.add(Entry(4f, 25f))
        entriesFirst.add(Entry(5f, 27f))
        entriesFirst.add(Entry(6f, 28f))
        entriesFirst.add(Entry(7f, 26f))
        entriesFirst.add(Entry(8f, 25f))
        entriesFirst.add(Entry(9f, 26f))
        entriesFirst.add(Entry(10f, 25f))
        entriesFirst.add(Entry(11f, 27f))
        entriesFirst.add(Entry(12f, 28f))

        val datasetFirst = LineDataSet(entriesFirst, "Температура")

        datasetFirst.setDrawFilled(true)

        val entriesSecond = ArrayList<Entry>()
        entriesSecond.add(Entry(1f, 94f))
        entriesSecond.add(Entry(2f, 85f))
        entriesSecond.add(Entry(3f, 70f))
        entriesSecond.add(Entry(4f, 80f))
        entriesSecond.add(Entry(5f, 75f))
        entriesSecond.add(Entry(6f, 90f))
        entriesSecond.add(Entry(7f, 87f))
        entriesSecond.add(Entry(8f, 78f))
        entriesSecond.add(Entry(9f, 98f))
        entriesSecond.add(Entry(10f, 80f))
        entriesSecond.add(Entry(11f, 67f))
        entriesSecond.add(Entry(12f, 60f))


        val datasetSecond = LineDataSet(entriesSecond, "Влажность")

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

//// Массив координат точек
//val entriesFirst = ArrayList<Entry>()
//entriesFirst.add(Entry(1f, 23f))
//entriesFirst.add(Entry(2f, 25f))
//entriesFirst.add(Entry(3f, 26f))
//entriesFirst.add(Entry(4f, 25f))
//entriesFirst.add(Entry(5f, 27f))
//entriesFirst.add(Entry(6f, 28f))
//entriesFirst.add(Entry(7f, 26f))
//entriesFirst.add(Entry(8f, 25f))
//entriesFirst.add(Entry(9f, 26f))
//entriesFirst.add(Entry(10f, 25f))
//entriesFirst.add(Entry(11f, 27f))
//entriesFirst.add(Entry(12f, 28f))
//
//val datasetFirst = LineDataSet(entriesFirst, "Температура")
//
//datasetFirst.setDrawFilled(true)
//
//val entriesSecond = ArrayList<Entry>()
//entriesSecond.add(Entry(1f, 94f))
//entriesSecond.add(Entry(2f, 85f))
//entriesSecond.add(Entry(3f, 70f))
//entriesSecond.add(Entry(4f, 80f))
//entriesSecond.add(Entry(5f, 75f))
//entriesSecond.add(Entry(6f, 90f))
//entriesSecond.add(Entry(7f, 87f))
//entriesSecond.add(Entry(8f, 78f))
//entriesSecond.add(Entry(9f, 98f))
//entriesSecond.add(Entry(10f, 80f))
//entriesSecond.add(Entry(11f, 67f))
//entriesSecond.add(Entry(12f, 60f))