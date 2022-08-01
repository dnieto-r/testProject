package com.example.fragmentstest.databases

import android.provider.BaseColumns

object DBData : BaseColumns {

    const val TABLE_NAME = "UsersData"
    const val COLUMN_NAME_ID = "id"
    const val COLUMN_NAME_NAME = "name"
    const val COLUMN_NAME_NUMBER = "number"
    const val COLUMN_NAME_ADDRESS = "address"
    const val COLUMN_NAME_PHOTO = "photo"
    const val COLUMN_NAME_ISFAVORITE = "isFavorite"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_NAME TEXT," +
                "$COLUMN_NAME_NUMBER TEXT," +
                "$COLUMN_NAME_ADDRESS TEXT," +
                "$COLUMN_NAME_PHOTO INTEGER," +
                "$COLUMN_NAME_ISFAVORITE BOOLEAN)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

}
