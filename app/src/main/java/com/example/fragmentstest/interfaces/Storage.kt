package com.example.fragmentstest.interfaces

import com.example.fragmentstest.models.User
import io.reactivex.rxjava3.core.Single

interface Storage {

    fun getRxUser(): Single<List<User>>

    fun editUser(user: User): Single<List<User>>

    fun addUser(user: User): Single<List<User>>

    fun removeUser(user: User): Single<List<User>>

}
