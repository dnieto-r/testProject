package com.example.fragmentstest.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fragmentstest.models.User
import io.reactivex.rxjava3.core.Single

interface Storage {

    fun getRxUser(): Single<List<User>>

    fun getUsers(): List<User>

    fun editUser(user: User)

    fun addUser(user: User)

    fun removeUser(user: User)

}
