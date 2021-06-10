package com.jandorresteijn.kiteapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

private val CHANNEL_ID = "i.apps.notifications"

class SyncService : Service() {

    private var mHandler: Handler? = null

    // task to be run here
    private val runnableService: Runnable = object : Runnable {
        override fun run() {
            syncData()
            // Repeat this runnable code block again every ... min
            mHandler?.postDelayed(this, DEFAULT_SYNC_INTERVAL)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create the Handler object
        mHandler = Handler()
        // Execute a runnable task as soon as possible
        mHandler!!.post(runnableService)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @Synchronized
    private fun syncData() {
        Log.e("iets", "nog ietsssss ")
        // call async http request
        // own ip here local host is blocked

        var url = "http://172.20.50.228:8000/wind_server"

        val queue = Volley.newRequestQueue(this)

// Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.e("request", "Response is: ${response.toString()}")
                if (response.toBoolean()) {
                    sendNotification()
                }
            },
            Response.ErrorListener { e -> Log.e("e", e.message!!) })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }

    fun sendNotification() {
        val name = "test"
        val descriptionText = "test"

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.person)
            .setContentTitle("Je kan kitennnn broer")
            .setContentText("De wind ligt lekker werken jij")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            with(NotificationManagerCompat.from(this)) {
                notificationManager.notify(1234, builder.build())
            }
        }



    }

    private fun createNotificationChannel() {

    }

    companion object {
        // default interval for syncing data
        const val DEFAULT_SYNC_INTERVAL = (30 * 100).toLong()
    }
}
