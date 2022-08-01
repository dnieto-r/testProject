package com.example.fragmentstest.databases

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.fragmentstest.models.User
import com.example.fragmentstest.interfaces.Storage

class LocalDBStorage(
    applicationContext: Context
) : Storage, SQLiteOpenHelper(applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {
    private val dbWrite = this.writableDatabase
    private val dbRead = this.readableDatabase

    @SuppressLint("Range")
    override fun getUsers(): MutableList<User> {
        var usersList: MutableList<User> = emptyList<User>().toMutableList()
        val projection = arrayOf(
            DBData.COLUMN_NAME_ID,
            DBData.COLUMN_NAME_NAME,
            DBData.COLUMN_NAME_NUMBER,
            DBData.COLUMN_NAME_ADDRESS,
            DBData.COLUMN_NAME_PHOTO,
            DBData.COLUMN_NAME_ISFAVORITE
        )

        val selection = "${DBData.COLUMN_NAME_ID} >= ?"
        val selectionArgs = arrayOf("0")

        val sortOrder = "${DBData.COLUMN_NAME_ID} DESC"

        dbRead.query(
            DBData.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        ).use {
            while (it.moveToNext()) {
                val user: User = User(
                    it.getString(it.getColumnIndex(DBData.COLUMN_NAME_ID)),
                    it.getString(it.getColumnIndex(DBData.COLUMN_NAME_NAME)),
                    it.getString(it.getColumnIndex(DBData.COLUMN_NAME_NUMBER)),
                    it.getString(it.getColumnIndex(DBData.COLUMN_NAME_ADDRESS)),
                    it.getInt(it.getColumnIndex(DBData.COLUMN_NAME_PHOTO)),
                    it.getString(it.getColumnIndex(DBData.COLUMN_NAME_ISFAVORITE)).toBoolean()
                )
                usersList.add(user)
            }
        }
        return usersList
    }

    override fun editUser(user: User) {
        val values = ContentValues().apply {
            put(DBData.COLUMN_NAME_ID, user.id)
            put(DBData.COLUMN_NAME_NAME, user.name)
            put(DBData.COLUMN_NAME_NUMBER, user.number)
            put(DBData.COLUMN_NAME_ADDRESS, user.address)
            put(DBData.COLUMN_NAME_PHOTO, user.photo)
            put(DBData.COLUMN_NAME_ISFAVORITE, user.isFavorite.toString())
        }

        val selection = "${DBData.COLUMN_NAME_ID} LIKE ?"
        val selectionArgs = arrayOf(user.id)
        dbWrite.update(
            DBData.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }

    override fun addUser(user: User) {
        val values = ContentValues().apply {
            put(DBData.COLUMN_NAME_ID, user.id)
            put(DBData.COLUMN_NAME_NAME, user.name)
            put(DBData.COLUMN_NAME_NUMBER, user.number)
            put(DBData.COLUMN_NAME_ADDRESS, user.address)
            put(DBData.COLUMN_NAME_PHOTO, user.photo)
            put(DBData.COLUMN_NAME_ISFAVORITE, user.isFavorite)
        }

        dbWrite?.insert(DBData.TABLE_NAME, null, values)
    }

    override fun removeUser(user: User) {
        val selection = "${DBData.COLUMN_NAME_ID} LIKE ?"
        val selectionArgs = arrayOf(user.id)
        dbWrite.delete(DBData.TABLE_NAME, selection, selectionArgs)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ContactsApp.db"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(DBData.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DBData.SQL_DELETE_ENTRIES)
        onCreate(p0)
    }

}
