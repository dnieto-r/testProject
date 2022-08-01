package com.example.fragmentstest.models

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentstest.R
import kotlin.properties.Delegates

class CustomAdapter(
    val selectUser: ((user: User, position: Int) -> Unit)
) : RecyclerView.Adapter<MyViewHolder>() {
    var selectedRow: Int = -1
    var usersList: List<User> by Delegates.observable(emptyList()) { _, old, new ->
        val diffUtil = MyDiffUtil(old, new)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        diffResults.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = usersList[position]
        if (position == selectedRow)
            holder.itemView.setBackgroundColor(Color.parseColor("#6200EE"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#BB86FC"));
        holder.onBindViewHolder(user = currentUser)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.row_main,
            parent, false
        )
        view.setOnClickListener {
            var selectedUser: User = User(
                usersList[viewType].id,
                usersList[viewType].name,
                usersList[viewType].number,
                usersList[viewType].address,
                usersList[viewType].photo,
                usersList[viewType].isFavorite
            )
            selectUser(selectedUser, viewType)
            selectedRow = viewType
            notifyDataSetChanged()
        }
        return MyViewHolder(
            view = view
        )
    }
}