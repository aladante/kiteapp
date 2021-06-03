package com.jandorresteijn.kiteapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

public class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("intent", intent!!.action.toString())
        if (intent!!.action == "com.jandorresteijn.LEAN") {
            val i = Intent(context, SyncService::class.java)
            if (context != null) {
                context.startService(i)
            }
        }
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.action)) {
            val i = Intent(context, SyncService::class.java)
            if (context != null) {
                context.startService(i)
            }
        }
    }
}