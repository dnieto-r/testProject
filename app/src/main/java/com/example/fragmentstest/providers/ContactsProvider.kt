package com.example.fragmentstest.providers

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import com.example.fragmentstest.R
import com.example.fragmentstest.models.User

class ContactsProvider (private val contentResolver: ContentResolver) {
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

    fun getInternalContacts(): List<User> {
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