package com.example.fragmentstest.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.fragmentstest.AIDLStore
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.R
import com.example.fragmentstest.models.User
import java.io.Serializable

class AIDLService : Service() {
    var contacts: MutableList<User> = emptyList<User>().toMutableList()
    private var selection: String =
        ContactsContract.Data.MIMETYPE + " IN ('" + Phone.CONTENT_ITEM_TYPE +
                "', '" + Email.CONTENT_ITEM_TYPE + "')"

    private val CHANNEL_ID = "ForegroundService Kotlin"

    private val binder = object : AIDLStore.Stub() {

        override fun getInternalContacts(): List<User> {
            var contacts: MutableList<User> = emptyList<User>().toMutableList()

            var cur: Cursor = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                projection,
                selection,
                null,
                null
            )!!

            if (cur != null) {
                var next = cur.moveToNext()
                while (next && cur.position < 375) {
                    val id: Long = cur.getLong(0)
                    val name: String = cur.getString(1)
                    val mime: String = cur.getString(2)
                    val data: String = cur.getString(3)
                    val photoId: Long = R.drawable.ic_launcher_background.toLong()
                    var user = User(id.toString(), name, data, mime, photoId, false)
                    contacts.add(user)
                    cur.moveToNext()
                }
            }
            return contacts
        }
    }

    private var projection = arrayOf(
        ContactsContract.Data.CONTACT_ID,
        ContactsContract.Data.DISPLAY_NAME,
        ContactsContract.Data.MIMETYPE,
        ContactsContract.Data.DATA1,
        ContactsContract.Data.DATA2,
        ContactsContract.Data.DATA3,
        ContactsContract.Data.PHOTO_ID
    )

    fun startService(context: Context) {
        val startIntent = Intent(context, AIDLService::class.java)
        ContextCompat.startForegroundService(context, startIntent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                updateNotification()
                mainHandler.postDelayed(this, 10000)
            }
        })
        return START_STICKY
    }

    private fun updateNotification() {
        contacts = binder.internalContacts.toMutableList()

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getText(R.string.app_name))
            .setContentText("Hay ${contacts.size} contactos disponibles")
            .setSmallIcon(R.drawable.ic_search)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager? = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    fun getUsers(): List<User> {
        return contacts;
    }
}