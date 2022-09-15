package com.example.fragmentstest.interactors

import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import javax.inject.Inject

class RemoveUserUseCase @Inject constructor(private val myStorage: Storage) {

    fun removeUser(user: User) {
        myStorage.removeUser(user)
    }

}
