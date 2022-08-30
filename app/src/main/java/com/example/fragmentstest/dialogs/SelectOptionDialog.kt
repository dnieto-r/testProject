package com.example.fragmentstest.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.models.User
import com.example.fragmentstest.R
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.Group
import com.example.fragmentstest.models.GroupsAdapter
import kotlinx.android.synthetic.main.dialog_select_option.*
import kotlinx.android.synthetic.main.fragment_blank.*

class SelectOptionDialog : DialogFragment() {

    private val myStorage: Storage by lazy {
        (this.context?.applicationContext as MyApplication).myDatabase
    }

    private var onCancel: (() -> Unit)? = null
    private lateinit var tnCreateGroup: Button
    private lateinit var tnEditGroup: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view = requireActivity().layoutInflater.inflate(R.layout.dialog_select_option, null)
        tnCreateGroup = view.findViewById(R.id.btn_create_group)
        tnEditGroup = view.findViewById(R.id.btn_edit_group)

        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
        val dialog = builder.create()

        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        tnCreateGroup.setOnClickListener {
            val createGroupDialog = CreateGroupDialog()
            createGroupDialog.show(
                requireActivity().supportFragmentManager,
                "createGroup"
            )
            dismiss()
        }
        tnEditGroup.setOnClickListener {
            val createGroupDialog = SelectGroupDialog()
            createGroupDialog.show(
                requireActivity().supportFragmentManager,
                "selectGroup"
            )
            dismiss()
        }
        return dialog
    }


}
