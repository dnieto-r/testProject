package com.example.fragmentstest.interactors

import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class AddUserUseCase(val myStorage: Storage) {

    fun addUser(user: User): Completable {
        return myStorage.addUser(user)
    }

}
