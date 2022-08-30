package com.example.fragmentstest.interfaces.useCases

import com.example.fragmentstest.models.User

interface EditUserUseCase {

    fun editUser(position: Int, user: User)

}
