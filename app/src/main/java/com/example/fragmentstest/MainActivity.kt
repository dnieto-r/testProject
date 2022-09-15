package com.example.fragmentstest

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentstest.fragments.FragmentBlank
import com.example.fragmentstest.fragments.FragmentDisplay
import com.example.fragmentstest.fragments.FragmentList
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.Group
import com.example.fragmentstest.models.User
import com.example.fragmentstest.presenters.MainActivityPresenter
import com.example.fragmentstest.views.MainActivityView
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity @Inject constructor() : DaggerAppCompatActivity(), MainActivityView {

    @Inject
    lateinit var myStorage: Storage

    @Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PermissionsManager().checkESPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE, this
        )
        if (myStorage.getGroups().isEmpty()) {
            myStorage.createGroup(Group(0, "Sin Grupo"));
        }
        setContentView(R.layout.activity_main)
        setupFragments()
    }

    override fun onDeleteSearch() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fl_fragment_display, FragmentBlank()
            )
            .commit()
        (supportFragmentManager.findFragmentById(R.id.fl_fragment_list)
                as FragmentList).onDeleteUser()
    }

    override fun setupFragments() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_fragment_list, FragmentList())
            .commit()
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fl_fragment_display,
                FragmentBlank()
            )
            .commit()
    }

    override fun onSelectUser(user: User, position: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_display, FragmentDisplay.newInstance(user, position))
            .commit()
    }

    override fun onCreateUser() {
        (supportFragmentManager.findFragmentById(R.id.fl_fragment_list) as FragmentList).onCreateUser()
    }

    override fun onEditUser() {
        (supportFragmentManager.findFragmentById(R.id.fl_fragment_list) as FragmentList).onEditUser()
    }

    fun addUser(user: User) {
        presenter.addUser(user)
    }

}

