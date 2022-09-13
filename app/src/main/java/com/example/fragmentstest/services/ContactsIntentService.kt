package com.example.fragmentstest.services

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.fragmentstest.models.User

class ContactsIntentService(name: String = "ContactIntentService") : IntentService(name) {

    override fun onHandleIntent(p0: Intent?) {
        Log.d("CIS", "CONTACTS INTENT SERVICE EN EJECUCIÓN")
    }

    fun getMobileContacts(): List<User> {
        Log.d("INFO", "Prueba")
        return emptyList()
    }

}