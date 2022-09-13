package com.example.fragmentstest.presenters

import android.util.Log
import com.example.fragmentstest.interactors.SearchUsersUseCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import com.example.fragmentstest.views.FragmentListView
import com.example.fragmentstest.views.MainActivityView
import javax.inject.Inject

class FragmentListPresenter @Inject constructor(
    private val listView: FragmentListView,
    private val mainActivityView: MainActivityView,
    private val searchUsersUseCase: SearchUsersUseCase,
) {

    fun performSearch(searchCondition: String) {
        Log.d("INFO", "Buscando la condición $searchCondition...")
        val users = searchUsersUseCase.getFilteredUsers(searchCondition)
        listView.displayFoundContacts(users)
    }

    fun selectUser(user: User, position: Int) {
        mainActivityView.onSelectUser(user, position)
    }

}
