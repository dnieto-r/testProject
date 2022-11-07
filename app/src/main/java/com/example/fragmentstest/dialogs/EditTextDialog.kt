package com.example.fragmentstest.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.fragmentstest.MainActivity
import com.example.fragmentstest.MyApplication
import com.example.fragmentstest.models.User
import com.example.fragmentstest.R
import com.example.fragmentstest.interfaces.Storage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.UUID

class EditTextDialog : DialogFragment() {

    private val myStorage: Storage by lazy {
        (this.context?.applicationContext as MyApplication).myDatabase
    }

    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etAddress: EditText
    private var onCancel: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view = requireActivity().layoutInflater.inflate(R.layout.dialog_edit_text, null)

        etName = view.findViewById(R.id.et_name)
        etNumber = view.findViewById(R.id.et_phone)
        etAddress = view.findViewById(R.id.et_address)

        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton(getString(R.string.create)) { _, _ ->
                val name = etName.text.toString()
                val number = etNumber.text.toString()
                val address = etAddress.text.toString()
                if (name != "" && number != "" && address != "") {
                    Log.d("INFO", "funxiona")

                    var user = User(
                        UUID.randomUUID().toString(),
                        etName.text.toString(),
                        etNumber.text.toString(),
                        etAddress.text.toString(),
                        R.drawable.ic_launcher_background.toLong(), false
                    )
                    (activity as MainActivity).addUser(user)
                } else {
                    Toast.makeText(
                        this.requireActivity().applicationContext,
                        getString(R.string.error_creating_user), Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                onCancel?.invoke()
            }
        val dialog = builder.create()

        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        return dialog
    }

}
