package com.example.fragmentstest.models

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_main.view.*

class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun onBindViewHolder(user: User) {
        view.circleImageView.setImageResource(user.photo)
        view.mr_tv_name.text = user.name
        view.mr_tv_number.text = user.number
        if (user.isFavorite)
            view.circleImageView.borderColor =
                Color.parseColor("#10B041")
    }

}
