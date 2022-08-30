package com.example.fragmentstest.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fragmentstest.interfaces.dao.GroupDao
import com.example.fragmentstest.interfaces.dao.UserDao
import com.example.fragmentstest.interfaces.dao.UserGroupDao
import com.example.fragmentstest.models.entities.GroupEntity
import com.example.fragmentstest.models.entities.UserEntity
import com.example.fragmentstest.models.entities.UserGroupEntity

@Database(entities = [UserEntity::class,
    GroupEntity::class,
    UserGroupEntity::class], version = 6)
abstract class RoomDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun groupDao(): GroupDao

    abstract fun userGroupDao(): UserGroupDao

}