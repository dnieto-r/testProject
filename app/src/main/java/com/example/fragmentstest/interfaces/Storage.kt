package com.example.fragmentstest.interfaces

import com.example.fragmentstest.models.Group
import com.example.fragmentstest.models.User
import com.example.fragmentstest.models.UserGroupEntity

interface Storage {

    fun getUsers(): List<User>

    fun editUser(user: User)

    fun addUser(user: User)

    fun removeUser(user: User)

    fun createGroup(group: Group)

    fun removeGroup(group: Group)

    fun getGroups(): List<Group>

    fun addUserToGroup(userId: String, groupId: Int)

    fun getGroup(userId: String): Group

    fun updateUserGroup(userId: String, groupId: Int)

}
