package com.example.teplica
import android.util.Log
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class PieSocketListener : WebSocketListener() {
    lateinit var web_socket : WebSocket

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.e("burak","baglandi")
        web_socket = webSocket
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        output("ffff: ${bytes}")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        output("Closing : $code / $reason")
    }


    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        output("Error : " + t.message+" fsda")
    }

    fun output(text: String?) {
        Log.d("PieSocket", text!!)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}