package com.example.fragmentstest.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.R
import com.example.fragmentstest.interactors.EditUserUseCase
import com.example.fragmentstest.interactors.RemoveUserUserCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.User
import com.example.fragmentstest.presenters.FragmentDisplayPresenter
import com.example.fragmentstest.views.FragmentDisplayView
import com.example.fragmentstest.views.MainActivityView
import kotlinx.android.synthetic.main.fragment_display.*


class FragmentDisplay : Fragment(), FragmentDisplayView {

    private val mainActivityView: MainActivityView by lazy { activity as MainActivity }

    var isFavorite: Boolean = false
    private var isEdited: Boolean = false
    var isUserSelected: Boolean = false

    lateinit var myStorage: Storage
    lateinit var presenter: FragmentDisplayPresenter

    companion object {
        fun newInstance(user: User, position: Int): FragmentDisplay {
            val f = FragmentDisplay()

            val args = Bundle()
            args.putSerializable("user", user)
            args.putInt("position", position)
            f.arguments = args

            return f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myStorage = (this.context?.applicationContext as MyApplication).myDatabase
        presenter = FragmentDisplayPresenter(
            this, EditUserUseCase(myStorage),
            RemoveUserUserCase(myStorage)
        )
    }

    override fun onResume() {
        super.onResume()
        val user = arguments?.getSerializable("user") as User
        val position = arguments?.getInt("position") ?: 0

        loadUserInfo(user)
        loadUserGroup(user)
        initializeEvents(myStorage.getUsers()[position], myStorage.getUsers(), position)
    }

    private fun loadUserInfo(user: User) {
        isUserSelected = true
        ti_name.setText(user.name)
        ti_number.setText(user.number)
        ti_address.setText(user.address)
        app_bar_image.setImageResource(user.photo)
        isFavorite = user.isFavorite
        if (isFavorite)
            btn_fav.setText(R.string.leave_fav)
        else
            btn_fav.setText(R.string.add_fav)

    }

    private fun loadUserGroup(user: User) {
        val groups = myStorage.getGroups().map { it.name }
        val arrayAdapter = ArrayAdapter(this.requireContext(), R.layout.dropdown_item, groups)
        val groupName = myStorage.getGroup(user.id)?.name
        val groupPosition = arrayAdapter.getPosition(groupName)

        s_group.adapter = arrayAdapter
        s_group.setSelection(groupPosition)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_display, container, false)
    }

    private fun initializeEvents(user: User, usersReference: List<User>, position: Int) {
        ti_name.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                this.setIsEditing(ti_name.text.toString(), user.name)
        }
        ti_number.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                this.setIsEditing(ti_number.text.toString(), user.number)
        }
        ti_address.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                this.setIsEditing(ti_address.text.toString(), user.address)
        }
        btn_fav.setOnClickListener {
            isFavorite = !isFavorite
            this.setIsEditing(isFavorite, user.isFavorite)
            if (isFavorite)
                btn_fav.setText(R.string.leave_fav)
            else
                btn_fav.setText(R.string.add_fav)
        }
        fab.setOnClickListener {
            if (isEdited) {
                val newUser: User = User(
                    user.id,
                    ti_name.text.toString(),
                    ti_number.text.toString(),
                    ti_address.text.toString(),
                    user.photo,
                    isFavorite
                )
                presenter.editUser(newUser, position)
            } else {
                AlertDialog.Builder(this.requireContext())
                    .setTitle(R.string.delete_contact)
                    .setMessage(R.string.delete_contact_conf)
                    .setPositiveButton(R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            presenter.removeUser(user)
                        })
                    .setNegativeButton(R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
        }
        s_group.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                myStorage.updateUserGroup(user.id, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setIsEditing(newData: Any, oldData: Any) {
        if (newData != oldData) {
            fab.backgroundTintList = this.resources.getColorStateList(R.color.green)
            fab.setImageResource(R.drawable.ic_edit)
            isEdited = true
        }
    }

    override fun onDeleteUser() {
        mainActivityView.onDeleteSearch()
    }

    override fun onEditUser() {
        isEdited = false
        mainActivityView.onEditUser()
        fab.backgroundTintList = this.resources.getColorStateList(R.color.red)
        fab.setImageResource(R.drawable.ic_delete)
    }

}