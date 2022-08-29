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
import android.os.IBinder
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.fragmentstest.IAIDLStore
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.R
import com.example.fragmentstest.models.User
import java.io.Serializable


class AIDLService : Service() {
    var contacts: MutableList<User> = emptyList<User>().toMutableList()

    private val binder = object : IAIDLStore.Stub() {
        override fun calculate(a: Int, b: Int): Int {
            return a + b
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
    private var selection: String =
        ContactsContract.Data.MIMETYPE + " IN ('" + Phone.CONTENT_ITEM_TYPE +
                "', '" + Email.CONTENT_ITEM_TYPE + "')"

    val CHANNEL_ID = "ForegroundService Kotlin"

    fun startService(context: Context) {
        val startIntent = Intent(context, AIDLService::class.java)
        ContextCompat.startForegroundService(context, startIntent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        updateNotification()
        return START_STICKY
    }

    fun updateNotification() {
        var cur: Cursor = contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            projection,
            selection,
            null,
            null
        )!!

        if (cur != null) {
            var next = cur.moveToNext()
            Log.d("NEXT", next.toString() + " " + cur.position)
            while (next && cur.position < 375) {
                Log.d("NUMBER", "${contacts.size}")
                val id: Long = cur.getLong(0)
                val name: String = cur.getString(1)
                val mime: String = cur.getString(2)
                val data: String = cur.getString(3)
                val photoId: Long = cur.getLong(6)
                var user = User(id.toString(), name, data, mime, photoId, false)
                contacts.add(user)
                cur.moveToNext()
            }
        }
        sendDataToActivity()
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

    private fun sendDataToActivity() {
        val sendLevel = Intent()
        sendLevel.action = "USERS_LIST"
        val args = Bundle()
        args.putSerializable("ARRAYLIST", contacts as Serializable)
        sendLevel.putExtra("USERS_LIST", args)
        sendBroadcast(sendLevel)
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