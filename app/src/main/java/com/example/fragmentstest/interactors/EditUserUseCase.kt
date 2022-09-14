package com.example.fragmentstest.interactors

import com.example.fragmentstest.interfaces.useCases.EditUserUseCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import javax.inject.Inject

class EditUserUseCase @Inject constructor(private val myStorage: Storage) {

    fun editUser(user: User) {
        myStorage.editUser(user)
    }

}
