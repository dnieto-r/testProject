package com.example.fragmentstest.databases

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import com.example.fragmentstest.services.ContactsIntentService
import io.reactivex.rxjava3.core.Single
import android.os.Bundle
import com.telefonica.movistarhome.iot.light.model.legacy.DataReceiverCallBack
import com.telefonica.movistarhome.iot.light.model.legacy.ISResultReceiver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class IntentServiceStorage(
    private val applicationContext: Context
) : Storage {
    private var myService: Intent? = null

    fun getContacts(): Single<List<User>> =
        Single.create { emitter ->
            startServiceForDataRecovery(
                resultReceiverCallBack = object : DataReceiverCallBack {
                    override fun onSuccess(data: Bundle) {
                        var usersList = data.getParcelableArrayList<User>("contacts") as List<User>
                        Log.d("INFO", usersList[0].toString())
                        emitter.onSuccess(usersList)
                    }

                    override fun onError(exception: Exception) {
                        emitter.onError(exception)
                    }
                })
        }

    private fun startServiceForDataRecovery(
        resultReceiverCallBack: DataReceiverCallBack
    ) {
        val isResultReceiver = ISResultReceiver(resultReceiverCallBack)
        myService = IntentServiceObject.newInstance(applicationContext)
        myService?.putExtra("key.contactsIntentService", isResultReceiver)
        applicationContext.startService(myService);
    }


    override fun getRxUser(): Single<List<User>> {
        return getContacts().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUsers(): List<User> {
        return emptyList()
    }

    override fun editUser(user: User) {
        var users = this.getUsers().toMutableList()

        val selectedUser = users.find { it.id == user.id }
        DataMemoryAbstraction.usersReference.remove(selectedUser)
        DataMemoryAbstraction.usersReference.add(user)
        DataMemoryAbstraction.usersReference.sortBy { it.name }
    }

    override fun addUser(user: User) {
        DataMemoryAbstraction.usersReference.add(user)
        DataMemoryAbstraction.usersReference.sortBy { it.name }
    }

    override fun removeUser(user: User) {
        DataMemoryAbstraction.usersReference.remove(user)
    }

}

class IntentServiceObject {
    companion object {
        var myServiceIntent: Intent? = null

        fun newInstance(applicationContext: Context): Intent? {
            if (myServiceIntent == null) {
                myServiceIntent = Intent(applicationContext,
                    ContactsIntentService()::class.java)

                applicationContext.startService(myServiceIntent)
            }
            return myServiceIntent
        }
    }
}
