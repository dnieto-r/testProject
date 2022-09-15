package com.example.fragmentstest.services

import android.app.IntentService
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.os.ResultReceiver
import android.provider.ContactsContract
import android.util.Log
import com.example.fragmentstest.R
import com.example.fragmentstest.models.User
import com.telefonica.movistarhome.iot.light.model.legacy.ISResultReceiver.Companion.sendError
import com.telefonica.movistarhome.iot.light.model.legacy.ISResultReceiver.Companion.sendSuccess

class ContactsIntentService(name: String = "ContactIntentService") : IntentService(name) {
    private var selection: String =
        ContactsContract.Data.MIMETYPE + " IN ('" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE +
                "', '" + ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE + "')"

    private var projection = arrayOf(
        ContactsContract.Data.CONTACT_ID,
        ContactsContract.Data.DISPLAY_NAME,
        ContactsContract.Data.MIMETYPE,
        ContactsContract.Data.DATA1,
        ContactsContract.Data.DATA2,
        ContactsContract.Data.DATA3,
        ContactsContract.Data.PHOTO_ID
    )

    override fun onHandleIntent(p0: Intent?) {
        val resultReceiver: ResultReceiver? =
            p0?.getParcelableExtra("key.contactsIntentService")
        val data = emptyList<User>()
        val bund = Bundle()
        bund.putParcelableArrayList("contacts", getInternalContacts())
        if (data != null) {
            resultReceiver?.sendSuccess(bund)
        } else {
            resultReceiver?.sendError(Exception("No available recovery data"))
        }
    }

    private fun getInternalContacts(): ArrayList<User> {
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
        return ArrayList(contacts)
    }
}