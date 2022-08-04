package com.example.fragmentstest

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionsManager {

    companion object {
        private const val STORAGE_PERMISSION_CODE = 102
        private const val READ_PERMISSION_CODE = 100
    }

    fun checkESPermission(permission: String, context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(permission),
                STORAGE_PERMISSION_CODE
            )
        } else {
            Toast.makeText(context, R.string.perms_granted, Toast.LENGTH_SHORT).show()
        }
    }

    fun checkContactsPermission(permission: String, context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(permission),
                READ_PERMISSION_CODE
            )
        } else {
            Toast.makeText(context, R.string.perms_granted, Toast.LENGTH_SHORT).show()
        }
    }
}
