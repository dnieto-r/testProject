package com.example.fragmentstest.interactors

import com.example.fragmentstest.interfaces.SearchUsersUseCase
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage

class SearchUsersUseCase(val myStorage: Storage) :
    SearchUsersUseCase {

    override fun getFilteredUsers(searchCondition: String): List<User> {
        return if (myStorage.getUsers() != null) {
            myStorage.getUsers().filter {
                it.name.toLowerCase().contains(searchCondition)
            }
        } else {
            emptyList()
        }
    }
}

