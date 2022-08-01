package com.example.fragmentstest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.R
import com.example.fragmentstest.dialogs.EditTextDialog
import kotlinx.android.synthetic.main.fragment_blank.*

class FragmentBlank : Fragment() {

    override fun onResume() {
        super.onResume()
        fab_createUser.setOnClickListener {
            val dialog = EditTextDialog()
            dialog.show(
                requireActivity().supportFragmentManager,
                "editDescription"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

}
