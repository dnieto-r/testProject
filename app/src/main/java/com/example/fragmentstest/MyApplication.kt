package com.example.fragmentstest

import android.app.Application
import android.util.Log
import com.example.fragmentstest.databases.RoomLocalDBStorage
import com.example.fragmentstest.interfaces.Storage

class MyApplication : Application() {

    val myDatabase: Storage by lazy {
        RoomLocalDBStorage(this) // Change this line to change the storage type
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("INFO", "Se ha iniciado la aplicación")
    }
}
