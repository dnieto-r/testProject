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
import com.example.fragmentstest.models.CustomCallback
import com.example.fragmentstest.models.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class AIDLService : Service() {
    private var selection: String =
        ContactsContract.Data.MIMETYPE + " IN ('" + Phone.CONTENT_ITEM_TYPE +
                "', '" + Email.CONTENT_ITEM_TYPE + "')"

    private var projection = arrayOf(
        ContactsContract.Data.CONTACT_ID,
        ContactsContract.Data.DISPLAY_NAME,
        ContactsContract.Data.MIMETYPE,
        ContactsContract.Data.DATA1,
        ContactsContract.Data.DATA2,
        ContactsContract.Data.DATA3,
        ContactsContract.Data.PHOTO_ID
    )
    
    private val binder = object : AIDLStore.Stub() {

        override fun getMobileContacts(output: CustomCallback) {
            Single.fromCallable{ getInternalContacts() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ it -> output.onResult(it) }
        }

    }

    private fun getInternalContacts(): List<User> {
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

}