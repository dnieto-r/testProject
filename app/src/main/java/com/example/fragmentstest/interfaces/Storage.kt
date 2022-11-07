package com.example.fragmentstest.interfaces

import com.example.fragmentstest.models.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface Storage {

    fun getRxUser(): Single<List<User>>

    fun editUser(user: User): Completable

    fun addUser(user: User): Completable

    fun removeUser(user: User): Completable

}
