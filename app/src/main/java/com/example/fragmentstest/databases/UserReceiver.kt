package com.example.fragmentstest.databases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class UserReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action.equals("USERS_LIST")) {
            var bundle = intent.getBundleExtra("USERS_LIST");
            val usersList = bundle?.getSerializable("ARRAYLIST")
        }

    }
}
