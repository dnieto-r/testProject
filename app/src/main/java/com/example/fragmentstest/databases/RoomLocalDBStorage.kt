package com.example.fragmentstest.databases

import android.content.Context
import androidx.room.Room
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import com.example.fragmentstest.models.toDC
import com.example.fragmentstest.models.toDao

class RoomLocalDBStorage(
    applicationContext: Context
) : Storage {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomDB::class.java, "contactsApp"
        ).allowMainThreadQueries().build()
    }

    override fun getUsers(): List<User> {
        val userDao = db.userDao()
        return userDao.getUsers().map { it.toDC() }
    }

    override fun editUser(user: User) {
        val userDao = db.userDao()
        userDao.editUser(user.toDao())
    }

    override fun addUser(user: User) {
        val userDao = db.userDao()
        userDao.addUser(user.toDao())
    }

    override fun removeUser(user: User) {
        val userDao = db.userDao()
        userDao.removeUser(user.toDao())
    }

}