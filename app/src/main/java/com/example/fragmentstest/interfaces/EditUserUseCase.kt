package com.example.fragmentstest.interfaces

import com.example.fragmentstest.models.User

interface EditUserUseCase {

    fun editUser(position: Int, user: User)

}
