package com.example.fragmentstest.databases

import android.content.Context
import android.util.Log
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.services.AIDLService

class AIDLStorage(
    private val applicationContext: Context
) : Storage {

    override fun getUsers(): List<User> {
        var users = (applicationContext as MyApplication).myService?.internalContacts
        Log.d("INFORMACION", users?.size.toString())
        return users ?: emptyList()
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
