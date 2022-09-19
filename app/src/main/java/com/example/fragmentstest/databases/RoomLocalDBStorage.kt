package com.example.fragmentstest.databases

import android.content.Context
import androidx.room.Room
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import com.example.fragmentstest.models.toDC
import com.example.fragmentstest.models.toDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomLocalDBStorage(
    applicationContext: Context
) : Storage {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomDB::class.java, "contactsApp"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }

    override fun getRxUser(): Single<List<User>> {
        val userDao = db.userDao()
        return Single.fromCallable {
            userDao.getUsers().map { it.toDC() }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun editUser(user: User): Completable {
        val userDao = db.userDao()
        userDao.editUser(user.toDao())
        return Completable.complete()
    }

    override fun addUser(user: User): Completable {
        val userDao = db.userDao()
        userDao.addUser(user.toDao())
        return Completable.complete()
    }

    override fun removeUser(user: User): Completable {
        val userDao = db.userDao()
        userDao.removeUser(user.toDao())
        return Completable.complete()
    }

}