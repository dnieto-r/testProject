package com.example.fragmentstest.models

import com.example.fragmentstest.models.entities.GroupEntity
import java.io.Serializable

class Group(
    var id: Int,
    var name: String
) : Serializable, ListableObject

fun Group.toDao() = GroupEntity(
    id = id,
    name = name
)
