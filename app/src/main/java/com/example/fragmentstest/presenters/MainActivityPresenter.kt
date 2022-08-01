package com.example.fragmentstest.presenters

import android.util.Log
import com.example.fragmentstest.interactors.AddUserUseCase
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.views.MainActivityView

class MainActivityPresenter(
    var displayView: MainActivityView?,
    private val AddUserUseCase: AddUserUseCase,
) {

    fun addUser(user: User) {
        Log.d("INFO", "Añadiendo el usuario $user...")
        AddUserUseCase.addUser(user)
        displayView?.onCreateUser()
    }

}
