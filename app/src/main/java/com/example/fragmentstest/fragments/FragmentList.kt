package com.example.fragmentstest.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.models.CustomAdapter
import com.example.fragmentstest.models.User
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.R
import com.example.fragmentstest.interactors.SearchUsersUseCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.presenters.FragmentListPresenter
import com.example.fragmentstest.views.FragmentListView
import kotlinx.android.synthetic.main.fragment_list.*

class FragmentList : Fragment(), FragmentListView {
    private val customAdapter by lazy { CustomAdapter(::onSelectUser) }

    private val myStorage: Storage by lazy {
        (this.context?.applicationContext as MyApplication).myDatabase
    }
    private val presenter: FragmentListPresenter by lazy {
        FragmentListPresenter(
            this, (activity as MainActivity),
            SearchUsersUseCase(myStorage)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupList()
        this.setupSearchInput()
    }

    override fun setupList() {
        if (myStorage.getUsers() != null)
            customAdapter.usersList = myStorage!!.getUsers()

        rv_users.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = customAdapter
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupSearchInput() {
        et_searchbar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("Before Text Changed: ", p0.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                et_searchbar.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_remove, 0);
                presenter.performSearch(p0.toString().toLowerCase())
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("After Text Changed: ", s.toString())
            }
        })
        et_searchbar.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= et_searchbar.right -
                    et_searchbar.compoundDrawables[2].bounds.width()
                ) {
                    this.clearSearch()
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    override fun displayFoundContacts(users: List<User>) {
        customAdapter.usersList = users
        customAdapter.selectedRow = -1
        customAdapter.notifyDataSetChanged()
    }

    override fun clearSearch() {
        et_searchbar.setText("")
        et_searchbar.setCompoundDrawablesWithIntrinsicBounds(
            0, 0,
            R.drawable.ic_search, 0
        );
        reloadUsers()
        (activity as MainActivity).onDeleteSearch()
    }

    private fun onSelectUser(user: User, position: Int) {
        presenter.selectUser(user, position)
    }

    fun onDeleteUser() {
        customAdapter.selectedRow = -1
        reloadUsers()
    }

    fun onCreateUser() {
        reloadUsers()
    }

    fun onEditUser() {
        reloadUsers()
    }

    private fun reloadUsers() {
        if (myStorage.getUsers() != null)
            customAdapter.usersList = myStorage!!.getUsers()
    }
}
