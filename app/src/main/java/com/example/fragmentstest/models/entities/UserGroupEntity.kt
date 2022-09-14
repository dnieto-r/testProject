package com.example.fragmentstest.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "userGroupList",
    primaryKeys = ["userId"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("groupId"),
                    onDelete = ForeignKey.CASCADE

        )]
    )
class UserGroupEntity(
    var userId: String,
    var groupId: Int
)

