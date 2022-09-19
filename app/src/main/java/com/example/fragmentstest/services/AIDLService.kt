package com.example.fragmentstest.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.fragmentstest.AIDLStore
import com.example.fragmentstest.models.CustomCallback
import com.example.fragmentstest.providers.ContactsProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class AIDLService : Service() {
    var contactsProvider: ContactsProvider = ContactsProvider(contentResolver)

    private val binder = object : AIDLStore.Stub() {

        override fun getMobileContacts(output: CustomCallback) {
            Single.fromCallable{ contactsProvider.getInternalContacts() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ it -> output.onResult(it) }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

}