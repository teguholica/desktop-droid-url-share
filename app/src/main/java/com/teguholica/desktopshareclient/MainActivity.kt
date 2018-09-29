package com.teguholica.desktopshareclient

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.nkzawa.socketio.client.IO
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val socket by lazy { IO.socket("http://192.168.1.68:3000") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        socket.on("command") {
            this.runOnUiThread {
                message.text = it[0] as String?
                goToSite(it[0] as String?)
            }
        }

        setContentView(R.layout.activity_main)
    }

    private fun goToSite(url: String?) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d("test", "page not found")
        }
    }

    override fun onPause() {
        if (socket.connected()) {
            socket.disconnect()
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (!socket.connected()) {
            socket.connect()
        }
    }
}
