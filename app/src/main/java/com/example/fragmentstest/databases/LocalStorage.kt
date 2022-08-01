package com.example.fragmentstest.databases

import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage

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

}
