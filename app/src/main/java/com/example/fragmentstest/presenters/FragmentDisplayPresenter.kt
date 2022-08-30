package com.example.fragmentstest.presenters

import android.util.Log
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.useCases.EditUserUseCase
import com.example.fragmentstest.interfaces.useCases.RemoveUserUseCase
import com.example.fragmentstest.views.FragmentDisplayView

class FragmentDisplayPresenter(
    var displayView: FragmentDisplayView?,
    private val editUserUseCase: EditUserUseCase,
    private val removeUserUseCase: RemoveUserUseCase
) {

    fun editUser(user: User, position: Int) {
        Log.d("INFO", "Cambiando información del usuario...")
        editUserUseCase.editUser(position, user)
        displayView?.onEditUser()
    }

    fun removeUser(user: User) {
        Log.d("INFO", "Eliminando el usuario $user...")
        removeUserUseCase.removeUser(user)
        displayView?.onDeleteUser()
    }

}
