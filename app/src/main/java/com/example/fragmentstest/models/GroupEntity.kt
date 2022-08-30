package com.example.fragmentstest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groupList")
class GroupEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "name") var name: String
)

fun GroupEntity.toDC() = Group(
    id = id,
    name = name
)
