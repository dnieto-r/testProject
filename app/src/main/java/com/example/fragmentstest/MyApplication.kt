package com.example.fragmentstest

import android.app.Application
import android.util.Log
import com.example.fragmentstest.databases.AIDLStorage
import com.example.fragmentstest.databases.RoomLocalDBStorage
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.services.AIDLService

class MyApplication : Application() {

    val myDatabase: Storage by lazy {
        RoomLocalDBStorage(this) // Change this line to change the storage type
    }

    val aidlService: AIDLService by lazy {
        var aidl = AIDLService()
        aidl.startService(this)
        aidl
    }

    override fun onCreate() {
        super.onCreate()

        Log.d("INFO", "Se ha iniciado la aplicación")
    }

}
