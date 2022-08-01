package com.example.fragmentstest.interactors

import com.example.fragmentstest.interfaces.EditUserUseCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User

class EditUserUseCase(val myStorage: Storage) : EditUserUseCase {

    override fun editUser(position: Int, user: User) {
        myStorage.editUser(user)
    }

}
