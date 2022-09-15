package com.example.fragmentstest.dialogs.groups

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.R
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.Group
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class EditGroupDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var myStorage: Storage

    private lateinit var etName: EditText
    private var onCancel: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view = requireActivity().layoutInflater.inflate(R.layout.dialog_create_group, null)
        val group = myStorage.getGroups()[arguments?.getInt("position") ?: 0]

        etName = view.findViewById(R.id.et_name)
        etName.hint = group.name

        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton(getString(R.string.modify)) { _, _ ->
                val name = etName.text.toString()
                if (name != "") {
                    val group = Group(group.id, name)
                    myStorage.updateGroup(group)
                } else {
                    Toast.makeText(
                        this.requireActivity().applicationContext,
                        getString(R.string.error_creating_user), Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton(getString(R.string.delete)) { _, _ ->
                myStorage.removeGroup(group)
                onCancel?.invoke()
            }

        val dialog = builder.create()

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        return dialog
    }

}
