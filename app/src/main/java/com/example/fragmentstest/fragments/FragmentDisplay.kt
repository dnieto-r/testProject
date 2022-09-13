package com.example.fragmentstest.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.models.User
import com.example.fragmentstest.R
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.presenters.FragmentDisplayPresenter
import com.example.fragmentstest.views.FragmentDisplayView
import com.example.fragmentstest.views.MainActivityView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_display.*
import javax.inject.Inject

class FragmentDisplay : DaggerFragment(), FragmentDisplayView {

    @Inject
    lateinit var myStorage: Storage

    @Inject
    lateinit var presenter: FragmentDisplayPresenter

    private val mainActivityView: MainActivityView
            by lazy { activity as MainActivity }

    var isFavorite: Boolean = false
    private var isEdited: Boolean = false
    var isUserSelected: Boolean = false

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

    override fun onResume() {
        super.onResume()
        val user = arguments?.getSerializable("user") as User
        val position = arguments?.getInt("position") ?: 0

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

        initializeEvents(myStorage.getUsers()[position], myStorage.getUsers(), position)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_display, container, false)
    }

    private fun initializeEvents(user: User, usersReference: List<User>, position: Int) {
        ti_name.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                this.setIsEditing(ti_name.text.toString(), user.name)
        })
        ti_number.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                this.setIsEditing(ti_number.text.toString(), user.number)
        })
        ti_address.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                this.setIsEditing(ti_address.text.toString(), user.address)
        })
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
                presenter.editUser(newUser)
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
    }

    private fun setIsEditing(newData: Any, oldData: Any) {
        if (newData != oldData) {
            fab.backgroundTintList = this.getResources().getColorStateList(R.color.green)
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
        fab.backgroundTintList = this.getResources().getColorStateList(R.color.red)
        fab.setImageResource(R.drawable.ic_delete)
    }

}