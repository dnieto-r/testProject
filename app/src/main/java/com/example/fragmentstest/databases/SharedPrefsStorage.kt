package com.example.fragmentstest.databases

import android.content.Context
import android.content.SharedPreferences
import com.example.fragmentstest.R
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

    override fun getUsers(): MutableList<User> {
        val contacts: String? = sharedPref.getString("users", "")
        return if (contacts != "")
            gson.fromJson(contacts, type)
        else
            emptyList<User>().toMutableList()
    }

    override fun editUser(user: User) {
        val users = getUsers()
        val selectedUser = users.find { it.id == user.id }
        users.remove(selectedUser)
        users.add(user)
        users.sortBy { it.name }
        saveList(users)
    }

    override fun addUser(user: User) {
        val usersList = getUsers()
        usersList.add(usersList.size, user)
        saveList(usersList)
    }

    override fun removeUser(user: User) {
        val usersList = getUsers()
        usersList.remove(user)
        usersList.sortBy { it.name }
        saveList(usersList)
    }

    private fun saveList(usersList: List<User>) {
        val usersInJson = gson.toJson(usersList)
        sharedPrefEditor.putString("users", usersInJson)
        sharedPrefEditor.commit();
    }

}
