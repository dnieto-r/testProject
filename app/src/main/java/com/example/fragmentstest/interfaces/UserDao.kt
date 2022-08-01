package com.example.fragmentstest.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fragmentstest.models.User
import com.example.fragmentstest.models.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM usersList")
    fun getUsers(): List<UserEntity>

    @Update
    fun editUser(user: UserEntity)

    @Insert
    fun addUser(user: UserEntity)

    @Delete
    fun removeUser(user: UserEntity)

}
