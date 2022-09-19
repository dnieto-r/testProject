package com.example.fragmentstest.databases

import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LocalStorage : Storage {

    override fun getRxUser(): Single<List<User>> {
        return Single.fromCallable { DataMemoryAbstraction.usersReference.toList() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun editUser(user: User): Single<List<User>> {
        return getRxUser()
            .map { it ->
                val selectedUser = it.find { it.id == user.id }
                DataMemoryAbstraction.usersReference.remove(selectedUser)
                DataMemoryAbstraction.usersReference.add(user)
                DataMemoryAbstraction.usersReference.sortBy { it.name }
                it
            }
    }

    override fun addUser(user: User): Single<List<User>> {
        DataMemoryAbstraction.usersReference.add(user)
        DataMemoryAbstraction.usersReference.sortBy { it.name }
        return Single.fromCallable{ emptyList() }
    }

    override fun removeUser(user: User): Single<List<User>> {
        DataMemoryAbstraction.usersReference.remove(user)
        return Single.fromCallable{ emptyList() }
    }

}
