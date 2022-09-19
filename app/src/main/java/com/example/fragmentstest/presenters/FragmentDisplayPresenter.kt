package com.example.fragmentstest.presenters

import android.util.Log
import com.example.fragmentstest.interactors.EditUserUseCase
import com.example.fragmentstest.interactors.RemoveUserUseCase
import com.example.fragmentstest.models.User
import com.example.fragmentstest.views.FragmentDisplayView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class FragmentDisplayPresenter(
    var displayView: FragmentDisplayView?,
    private val editUserUseCase: EditUserUseCase,
    private val removeUserUseCase: RemoveUserUseCase
) {

    fun editUser(user: User, position: Int) {
        Log.d("INFO", "Cambiando información del usuario...")
        editUserUseCase.editUser(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                displayView?.onEditUser()
            }
    }

    fun removeUser(user: User) {
        Log.d("INFO", "Eliminando el usuario $user...")
        removeUserUseCase.removeUser(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                displayView?.onDeleteUser()
            }
    }

}
