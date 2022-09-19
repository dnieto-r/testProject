package com.example.fragmentstest.presenters

import com.example.fragmentstest.interactors.SearchUsersUseCase
import com.example.fragmentstest.models.User
import com.example.fragmentstest.views.FragmentListView
import com.example.fragmentstest.views.MainActivityView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class FragmentListPresenter(
    private var listView: FragmentListView?,
    var mainActivityView: MainActivityView?,
    private val searchUsersUseCase: SearchUsersUseCase,
) {

    fun performSearch(searchCondition: String) {
        this.searchUsersUseCase.getFilteredUsers(searchCondition)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                listView?.displayFoundContacts(it)
            }

    }

    fun selectUser(user: User, position: Int) {
        mainActivityView?.onSelectUser(user, position)
    }

}
