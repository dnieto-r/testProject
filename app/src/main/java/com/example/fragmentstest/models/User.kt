package com.example.fragmentstest.models

import java.io.Serializable

data class User(
    var id: String,
    var name: String,
    var number: String,
    var address: String,
    var photo: Int,
    var isFavorite: Boolean
) : Serializable

fun User.toDao() = UserDao(
    id = id,
    name = name,
    number = number,
    address = address,
    photo = photo,
    isFavorite = isFavorite
)
