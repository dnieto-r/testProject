package com.example.fragmentstest.models

import com.example.fragmentstest.models.entities.GroupEntity
import java.io.Serializable

data class Group(
    var id: Int,
    var name: String
) : Serializable

fun Group.toDao() = GroupEntity(
    id = id,
    name = name
)
