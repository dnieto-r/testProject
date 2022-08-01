package com.example.fragmentstest.interactors

import com.example.fragmentstest.interfaces.RemoveUserUseCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User

class RemoveUserUserCase(val myStorage: Storage) : RemoveUserUseCase {

    override fun removeUser(user: User) {
        myStorage.removeUser(user)
    }

}
