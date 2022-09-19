package com.example.fragmentstest.databases

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.fragmentstest.AIDLStore
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.CustomCallback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers.io

class AIDLStorage(
    private val applicationContext: Context
) : Storage {
    private var myService: AIDLStore? = null

    private fun initialize() {
        myService = AIDLObject.newInstance(applicationContext)
    }

    override fun getRxUser(): Single<List<User>> {
        val getUsers = Single.create<List<User>>{ emitter ->
            initialize()

            val customCallback = object : CustomCallback.Stub() {
                override fun onResult(result: List<User>){
                    emitter.onSuccess(result)
                }
            }
            Log.d("INFO", myService.toString())
            myService?.getMobileContacts(customCallback)
        }.subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())

        return getUsers
    }

    override fun editUser(user: User): Completable {
        getRxUser()
            .map { it ->
                val selectedUser = it.find { it.id == user.id }
                DataMemoryAbstraction.usersReference.remove(selectedUser)
                DataMemoryAbstraction.usersReference.add(user)
                DataMemoryAbstraction.usersReference.sortBy { it.name }
            }
        return Completable.complete()
    }

    override fun addUser(user: User): Completable {
        DataMemoryAbstraction.usersReference.add(user)
        DataMemoryAbstraction.usersReference.sortBy { it.name }
        return Completable.complete()
    }

    override fun removeUser(user: User): Completable {
        DataMemoryAbstraction.usersReference.remove(user)
        return Completable.complete()
    }

}

class AIDLObject {
    companion object {
        var myService: AIDLStore? = null

        private val mConnection: ServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName, service: IBinder) {
                myService = AIDLStore.Stub.asInterface(service)
            }

            override fun onServiceDisconnected(className: ComponentName) {
                myService = null
            }
        }

        fun newInstance(applicationContext: Context): AIDLStore? {
            if (myService == null) {
                val i = Intent()

                i.setClassName(
                    "com.example.fragmentstest",
                    "com.example.fragmentstest.services.AIDLService"
                )

                applicationContext.bindService(
                    i,
                    mConnection,
                    Context.BIND_AUTO_CREATE
                )
            }
            return myService
        }
    }
}
