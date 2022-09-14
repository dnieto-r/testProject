package com.example.fragmentstest.models

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_group.view.*

class MyViewHolderGroup(private val view: View) : RecyclerView.ViewHolder(view) {

    fun onBindViewHolder(group: Group) {
        view.tv_group_name.text = group.name
    }

}
