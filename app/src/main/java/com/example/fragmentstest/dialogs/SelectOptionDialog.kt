package com.example.fragmentstest.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.fragmentstest.R

class SelectOptionDialog : DialogFragment() {
    private lateinit var tnCreateGroup: Button
    private lateinit var tnEditGroup: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view = requireActivity().layoutInflater.inflate(R.layout.dialog_select_option, null)

        tnCreateGroup = view.findViewById(R.id.btn_create_group)
        tnEditGroup = view.findViewById(R.id.btn_edit_group)

        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
        val dialog = builder.create()

        registerButtonEvents()

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        return dialog
    }

    private fun registerButtonEvents() {
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
    }

}
