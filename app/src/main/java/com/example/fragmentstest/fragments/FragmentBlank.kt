package com.example.fragmentstest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentstest.R
import com.example.fragmentstest.dialogs.EditUserDialogFragment
import com.example.fragmentstest.dialogs.groups.SelectOptionDialogFragment
import kotlinx.android.synthetic.main.fragment_blank.*

class FragmentBlank : Fragment() {

    override fun onResume() {
        super.onResume()
        fab_createUser.setOnClickListener {
            val dialog = EditUserDialogFragment()
            dialog.show(
                requireActivity().supportFragmentManager,
                "editDescription"
            )
        }
        fab_createGroup.setOnClickListener {
            val dialog = SelectOptionDialogFragment()
            dialog.show(
                requireActivity().supportFragmentManager,
                "selectOption"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

}
