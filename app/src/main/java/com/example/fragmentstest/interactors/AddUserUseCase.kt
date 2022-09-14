package com.example.fragmentstest.interactors

import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val myStorage: Storage
) {

    fun addUser(user: User) {
        myStorage.addUser(user)
        myStorage.addUserToGroup(user.id, 0)
    }

}
