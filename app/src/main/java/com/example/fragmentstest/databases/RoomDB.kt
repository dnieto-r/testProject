package com.example.fragmentstest.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fragmentstest.interfaces.GroupDao
import com.example.fragmentstest.interfaces.UserDao
import com.example.fragmentstest.interfaces.UserGroupDao
import com.example.fragmentstest.models.GroupEntity
import com.example.fragmentstest.models.UserEntity
import com.example.fragmentstest.models.UserGroupEntity

@Database(entities = [UserEntity::class,
    GroupEntity::class,
    UserGroupEntity::class], version = 5)
abstract class RoomDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun groupDao(): GroupDao

    abstract fun userGroupDao(): UserGroupDao

}