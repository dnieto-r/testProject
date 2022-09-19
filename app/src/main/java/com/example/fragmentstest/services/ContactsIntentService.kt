package com.example.fragmentstest.services

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import com.example.fragmentstest.models.User
import com.example.fragmentstest.providers.ContactsProvider
import com.telefonica.movistarhome.iot.light.model.legacy.ISResultReceiver.Companion.sendError
import com.telefonica.movistarhome.iot.light.model.legacy.ISResultReceiver.Companion.sendSuccess

class ContactsIntentService(name: String = "ContactIntentService") : IntentService(name) {
    var contactsProvider: ContactsProvider = ContactsProvider(contentResolver)

    override fun onHandleIntent(p0: Intent?) {
        val resultReceiver: ResultReceiver? =
            p0?.getParcelableExtra("key.contactsIntentService")
        val data = emptyList<User>()
        val bund = Bundle()
        bund.putParcelableArrayList("contacts", contactsProvider.getInternalContacts() as ArrayList<User>)
        if (data != null) {
            resultReceiver?.sendSuccess(bund)
        } else {
            resultReceiver?.sendError(Exception("No available recovery data"))
        }
    }
}