package com.example.fragmentstest

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.example.fragmentstest.databases.AIDLStorage
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.services.AIDLService

class MyApplication : Application() {

    val myDatabase: Storage by lazy {
        AIDLStorage(this) // Change this line to change the storage type
    }

    var myService: AIDLStore? = null

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            myService = AIDLStore.Stub.asInterface(service)
            if (myService != null) {
                try {
                    Log.i("Value from AIDL",
                        myService?.internalContacts?.size.toString() + "")
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {
            myService = null
        }
    }

    override fun onCreate() {
        super.onCreate()
        val i = Intent()

        i.setClassName("com.example.fragmentstest",
            "com.example.fragmentstest.services.AIDLService")

        bindService(i,
            mConnection,
            Context.BIND_AUTO_CREATE)

        AIDLService().startService(this) // Activa notificaciones
        Log.d("INFO", "Se ha iniciado la aplicación")
    }

}
