package com.jandorresteijn.kiteapp

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log



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
        val url = "http://www.localhost:8000/wind_server"


    }

    companion object {
        // default interval for syncing data
        const val DEFAULT_SYNC_INTERVAL = (30 * 100).toLong()
    }
}
