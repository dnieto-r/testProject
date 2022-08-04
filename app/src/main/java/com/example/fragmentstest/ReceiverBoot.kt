package com.example.fragmentstest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ReceiverBoot : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent: Intent = Intent()
        serviceIntent.setAction("AIDLService")
    }
}