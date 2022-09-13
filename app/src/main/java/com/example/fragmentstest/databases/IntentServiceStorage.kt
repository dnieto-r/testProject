package com.example.fragmentstest.databases

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.services.ContactsIntentService
import io.reactivex.rxjava3.core.Single

class IntentServiceStorage(
    private val applicationContext: Context
) : Storage {
    private var myService: Intent? = null

    private fun initialize() {
        myService = IntentServiceObject.newInstance(applicationContext)
    }

    override fun getRxUser(): Single<List<User>> {
        initialize()

        Log.d("INFO", myService.toString())
        (myService as ContactsIntentService).getMobileContacts()

        return Single.fromCallable{emptyList()}
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
