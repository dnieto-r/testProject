package com.example.fragmentstest.dialogs.groups

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentstest.R
import com.example.fragmentstest.interfaces.Storage
import com.example.fragmentstest.models.adapters.GroupsAdapter
import com.example.fragmentstest.presenters.SelectGroupDialogPresenter
import com.example.fragmentstest.views.SelectGroupDialogView
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class SelectGroupDialogFragment : DaggerDialogFragment(), SelectGroupDialogView {

    @Inject
    lateinit var myStorage: Storage

    @Inject
    lateinit var presenter: SelectGroupDialogPresenter

    private val groupsAdapter by lazy {
        val adapter = GroupsAdapter(presenter)
        adapter.groupList = myStorage.getGroups().drop(1)
        adapter
    }

    private var onCancel: (() -> Unit)? = null
    private lateinit var rvGroups: RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view = requireActivity().layoutInflater.inflate(R.layout.dialog_select_group, null)
        rvGroups = view.findViewById(R.id.rv_groups)

        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                onCancel?.invoke()
            }

        val dialog = builder.create()
        rvGroups.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = groupsAdapter
        }

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        return dialog
    }

    override fun onSelectGroup(groupPosition: Int) {
        val newEditGroupFragment = EditGroupDialogFragment()
        val args = Bundle()

        args.putInt("position", groupPosition)
        newEditGroupFragment.arguments = args
        dismiss()
        newEditGroupFragment.show(requireActivity().supportFragmentManager, "editGroup")
    }

}
