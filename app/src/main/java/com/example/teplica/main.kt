package com.example.teplica
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.example.teplica.data.settings
import com.example.teplica.data.state
import com.example.teplica.databinding.ActivityMainBinding
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject

class PieSocketListener(val context: Context,val funUpdateSettings: (settings:settings) -> Unit,
                        val funUpdateState: (state) -> Unit) : WebSocketListener() {
    lateinit var web_socket : WebSocket
    lateinit var settingsInfo: settings
    lateinit var stateInfo: state



    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.e("burak","baglandi")
        web_socket = webSocket
    }

    override fun onMessage(webSocket: WebSocket, byteString: ByteString) {
        try {
            val info = JSONObject(byteString.utf8()).getString("info_to")
            val data = JSONObject(byteString.utf8()).getJSONObject("info")
            when(info){
               "server_settings" -> {
                   convertToDataSettings(data)
                   (context as Activity).runOnUiThread{
                       funUpdateSettings(settingsInfo)
                   }
               }
               "server_state" ->{
                   convertToDataState(data)
                   (context as Activity).runOnUiThread{
                       funUpdateState(stateInfo)
                   }
               }
            }
        }catch (e:Exception){
            Log.d("mLog","${e}")
        }

    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)

    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    }

    fun setData(state: state, settings: settings) {
        stateInfo = state
        settingsInfo = settings
    }

    fun convertToDataSettings(info:JSONObject){
        settingsInfo.temp = info.getInt("temp")
        settingsInfo.auto_mode = info.getInt("auto_mode")
        settingsInfo.grass = info.getInt("grass")
        settingsInfo.light = info.getInt("light")
        settingsInfo.cooling = info.getInt("cooling")
        settingsInfo.watering = info.getInt("watering")

    }
    fun convertToDataState(info:JSONObject){
        stateInfo.temp = info.getInt("temp")
        stateInfo.water_level = info.getInt("water_level")
        stateInfo.humidity_air = info.getInt("humidity_air")
        stateInfo.lighting_level = info.getInt("lighting_level")
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}