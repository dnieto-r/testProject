package com.example.fragmentstest.databases

import android.content.Context
import androidx.room.Room
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.Group
import com.example.fragmentstest.models.User
import com.example.fragmentstest.models.entities.UserGroupEntity
import com.example.fragmentstest.models.toDao
import com.example.fragmentstest.models.entities.toDC

class RoomLocalDBStorage(
    applicationContext: Context
) : Storage {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomDB::class.java, "contactsApp"
        ).allowMainThreadQueries().fallbackToDestructiveMigration()
            .build()
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

    override fun createGroup(group: Group) {
        val groupDao = db.groupDao()
        groupDao.createGroup(group.toDao())
    }

    override fun getGroups(): List<Group> {
        val groupDao = db.groupDao()
        return groupDao.getGroups().map { it.toDC() }
    }

    override fun removeGroup(group: Group) {
        val groupDao = db.groupDao()
        groupDao.removeGroup(group.toDao())
    }

    override fun addUserToGroup(userId: String, groupId: Int) {
        val userGroupDao = db.userGroupDao()
        val newUserGroup = UserGroupEntity(userId, groupId)
        userGroupDao.addUserGroup(newUserGroup)
    }

    override fun getGroup(userId: String): Group?  {
        val userGroupDao = db.userGroupDao()
        return userGroupDao.getGroup(userId)?.toDC()
    }

    override fun updateUserGroup(userId: String, groupId: Int) {
        val userGroupDao = db.userGroupDao()
        val newUserGroup = UserGroupEntity(userId, groupId)
        userGroupDao.updateUserGroup(newUserGroup)
    }

    override fun updateGroup(group: Group) {
        val groupDao = db.groupDao()
        groupDao.updateGroup(group.toDao())
    }

}