package com.example.fragmentstest.models

import android.os.Parcelable
import com.example.fragmentstest.models.UserEntity
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class User(
    var id: String,
    var name: String,
    var number: String,
    var address: String,
    var photo: Long,
    var isFavorite: Boolean
) : Parcelable

fun User.toDao() = UserEntity(
    id = id,
    name = name,
    number = number,
    address = address,
    photo = photo,
    isFavorite = isFavorite
)
