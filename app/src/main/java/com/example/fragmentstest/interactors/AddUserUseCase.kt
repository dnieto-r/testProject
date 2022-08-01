package com.example.fragmentstest.interactors

import com.example.fragmentstest.interfaces.AddUserUseCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User

class AddUserUseCase(val myStorage: Storage) : AddUserUseCase {

    override fun addUser(user: User) {
        myStorage.addUser(user)
    }

}
