package com.example.fragmentstest.models

import java.io.Serializable

data class User(
    var id: String,
    var name: String,
    var number: String,
    var address: String,
    var photo: Long,
    var isFavorite: Boolean
) : Serializable

fun User.toDao() = UserEntity(
    id = id,
    name = name,
    number = number,
    address = address,
    photo = photo,
    isFavorite = isFavorite
)
