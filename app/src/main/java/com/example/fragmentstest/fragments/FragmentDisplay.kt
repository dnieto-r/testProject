package com.example.fragmentstest.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.models.User
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.R
import com.example.fragmentstest.interactors.EditUserUseCase
import com.example.fragmentstest.interactors.RemoveUserUseCase
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.presenters.FragmentDisplayPresenter
import com.example.fragmentstest.views.FragmentDisplayView
import com.example.fragmentstest.views.MainActivityView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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
            val fd = FragmentDisplay()

            val args = Bundle()
            args.putParcelable("user", user)
            args.putInt("position", position)
            fd.arguments = args

            return fd
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myStorage = (this.context?.applicationContext as MyApplication).myDatabase
        presenter = FragmentDisplayPresenter(
            this, EditUserUseCase(myStorage),
            RemoveUserUseCase(myStorage)
        )
    }

    override fun onResume() {
        super.onResume()
        val user = arguments?.getParcelable<User>("user")
        val position = arguments?.getInt("position") ?: 0
        var users = emptyList<User>()

        isUserSelected = true
        ti_name.setText(user?.name)
        ti_number.setText(user?.number)
        ti_address.setText(user?.address)
        app_bar_image.setImageResource(user?.photo?.toInt() ?: 0)
        isFavorite = user?.isFavorite ?: false
        if (isFavorite)
            btn_fav.setText(R.string.leave_fav)
        else
            btn_fav.setText(R.string.add_fav)

        myStorage.getRxUser().observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                users = it
                initializeEvents(users, position)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_display, container, false)
    }

    private fun initializeEvents(usersReference: List<User>, position: Int) {
        val user = usersReference[position]
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