package com.example.fragmentstest.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fragmentstest.interfaces.UserDao
import com.example.fragmentstest.models.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun userDao(): UserDao

}