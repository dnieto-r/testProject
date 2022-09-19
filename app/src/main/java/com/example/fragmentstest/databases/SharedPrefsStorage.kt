package com.example.fragmentstest.databases

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.fragmentstest.R
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SharedPrefsStorage(
    private val activityContext: Context
) : Storage {

    private val type = object : TypeToken<MutableList<User>?>() {}.type

    private val sharedPref: SharedPreferences by lazy {
        activityContext.getSharedPreferences(
            activityContext.getString(R.string.error_creating_user),
            Context.MODE_PRIVATE
        )
    }
    private val sharedPrefEditor: SharedPreferences.Editor by lazy {
        sharedPref.edit()
    }

    private val gson by lazy { Gson() }

    override fun getRxUser(): Single<List<User>> {
        val contacts: String? = sharedPref.getString("users", "")

        return Single.fromCallable<List<User>> {
            if (contacts != "") {
                gson.fromJson(contacts, type) as List<User>
            } else {
                emptyList()
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addUser(user: User): Completable {
        getRxUser()
            .map {
                val tempUserList = it.toMutableList()

                tempUserList.add(tempUserList.size, user)
                saveList(tempUserList)
                tempUserList
            }
        return Completable.complete()
    }

    override fun editUser(user: User): Completable {
        getRxUser()
            .map { it ->
                val selectedUser = it.find { it.id == user.id }
                val tempUserList = it.toMutableList()

                tempUserList.remove(selectedUser)
                tempUserList.add(user)
                tempUserList.sortBy { it.name }
                saveList(tempUserList)
            }
        return Completable.complete()
    }

    override fun removeUser(user: User): Completable{
        getRxUser().map { it ->
            val tempUserList = it.toMutableList()

            tempUserList.remove(user)
            tempUserList.sortBy { it.name }
            saveList(tempUserList)
        }
        return Completable.complete()
    }

    private fun saveList(usersList: List<User>) {
        val usersInJson = gson.toJson(usersList)
        sharedPrefEditor.putString("users", usersInJson)
        sharedPrefEditor.commit();
    }

}
