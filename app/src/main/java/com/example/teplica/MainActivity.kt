package com.example.teplica

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teplica.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client: OkHttpClient =  OkHttpClient()

//        binding.connect.setOnClickListener {
//            Log.d("PieSocket","Connecting");
//            val apiKey = "VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV"; //Demo key, get yours at https://piesocket.com
//            val channelId = 1;
//
//            val request: Request = Request
//                .Builder()
//                .url("ws://192.168.1.78:8080/connect")
//                .build()
//            val listener = PieSocketListener()
//            val ws: WebSocket = client.newWebSocket(request, listener)
//        }

    }
}