package com.example.fragmentstest.databases

import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LocalStorage : Storage {

    override fun getRxUser(): Single<List<User>> {
        return Single.fromCallable { DataMemoryAbstraction.usersReference.toList() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun editUser(user: User): Completable {
        getRxUser()
            .map { it ->
                val selectedUser = it.find { it.id == user.id }
                DataMemoryAbstraction.usersReference.remove(selectedUser)
                DataMemoryAbstraction.usersReference.add(user)
                DataMemoryAbstraction.usersReference.sortBy { it.name }
            }
        return Completable.complete()
    }

    override fun addUser(user: User): Completable {
        DataMemoryAbstraction.usersReference.add(user)
        DataMemoryAbstraction.usersReference.sortBy { it.name }
        return Completable.complete()
    }

    override fun removeUser(user: User): Completable {
        DataMemoryAbstraction.usersReference.remove(user)
        return Completable.complete()
    }

}
