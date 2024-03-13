package com.example.teplica

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teplica.databinding.ActivityMainBinding
import lecho.lib.hellocharts.view.LineChartView
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var ws:WebSocket

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
        setContentView(R.layout.activity_main)
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

}