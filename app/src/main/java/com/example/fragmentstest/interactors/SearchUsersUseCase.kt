package com.example.fragmentstest.interactors

import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val myStorage: Storage){

    fun getFilteredUsers(searchCondition: String): List<User> {
        return if (myStorage.getUsers() != null) {
            myStorage.getUsers().filter {
                it.name.toLowerCase().contains(searchCondition)
            }
        } else {
            emptyList()
        }
    }
}

