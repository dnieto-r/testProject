package com.example.fragmentstest.interactors

import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single

class SearchUsersUseCase(val myStorage: Storage) {

    fun getFilteredUsers(searchCondition: String): Single<List<User>> {
         return myStorage.getRxUser().observeOn(AndroidSchedulers.mainThread())
            .map { it ->
                it.filter {
                    it.name.toLowerCase().contains(searchCondition)
                }
            }
    }
}

