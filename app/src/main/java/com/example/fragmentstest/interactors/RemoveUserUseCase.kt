package com.example.fragmentstest.interactors

import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RemoveUserUseCase(val myStorage: Storage) {

    fun removeUser(user: User): Completable {
        return myStorage.removeUser(user)
    }

}
