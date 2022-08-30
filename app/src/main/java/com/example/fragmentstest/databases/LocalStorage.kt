package com.example.fragmentstest.databases

import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.Group

class LocalStorage : Storage {

    override fun getUsers(): List<User> = DataMemoryAbstraction.usersReference.toList()

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

    override fun createGroup(group: Group) {}

    override fun getGroups(): List<Group> {
        return emptyList()
    }

    override fun removeGroup(group: Group) {}

    override fun addUserToGroup(userId: String, groupId: Int) {}

    override fun getGroup(userId: String): Group {
        return Group(0, "Sin Grupo")
    }

    override fun updateUserGroup(userId: String, groupId: Int) {}

    override fun updateGroup(group: Group) {}

}
