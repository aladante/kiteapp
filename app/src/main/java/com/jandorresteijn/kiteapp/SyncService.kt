package com.jandorresteijn.kiteapp

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


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
        var url = "http://<YOUR_IP_HERE>:8000/wind_server"

        val queue = Volley.newRequestQueue(this)

// Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.e("request", "Response is: ${response.toString()}")
            },
            Response.ErrorListener { e -> Log.e("e", e.message!!)})

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }


    companion object {
        // default interval for syncing data
        const val DEFAULT_SYNC_INTERVAL = (30 * 100).toLong()
    }
}
