package com.example.fragmentstest.interfaces

import com.example.fragmentstest.models.User

interface SearchUsersUseCase {

    fun getFilteredUsers(searchCondition: String): List<User>

}
