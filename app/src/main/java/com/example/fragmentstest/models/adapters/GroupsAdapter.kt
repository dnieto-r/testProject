package com.example.fragmentstest.models.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentstest.R
import com.example.fragmentstest.dialogs.EditGroupDialog
import com.example.fragmentstest.dialogs.SelectGroupDialog
import com.example.fragmentstest.models.Group
import com.example.fragmentstest.models.MyDiffUtil
import com.example.fragmentstest.models.MyViewHolderGroup
import kotlin.properties.Delegates

class GroupsAdapter(
    var fragmentManager: FragmentManager,
    var selectGroupDialog: SelectGroupDialog
) : RecyclerView.Adapter<MyViewHolderGroup>() {
    var selectedRow = -1
    var groupList: List<Group> by Delegates.observable(emptyList()) { _, old, new ->
        val diffUtil = MyDiffUtil(old, new)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        diffResults.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun onBindViewHolder(holder: MyViewHolderGroup, position: Int) {
        val currentGroup = groupList[position]
        holder.onBindViewHolder(group = currentGroup)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderGroup {
        var view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.row_group,
            parent, false
        )
        val newEditGroupFragment = EditGroupDialog()

        view.setOnClickListener {
            selectedRow = viewType
            val args = Bundle()
            args.putInt("position", viewType + 1)
            newEditGroupFragment.arguments = args
            selectGroupDialog.dismiss()
            newEditGroupFragment.show(fragmentManager, "editGroup")
        }
        return MyViewHolderGroup(
            view = view
        )
    }
}