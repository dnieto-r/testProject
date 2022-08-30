package com.example.fragmentstest.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fragmentstest.models.Group
import com.example.fragmentstest.models.GroupEntity
import com.example.fragmentstest.models.UserEntity
import com.example.fragmentstest.models.UserGroupEntity

@Dao
interface UserGroupDao {


    @Insert
    fun addUserGroup(userGroup: UserGroupEntity)

    @Update
    fun updateUserGroup(userGroup: UserGroupEntity)

    @Delete
    fun removeUserGroup(userGroup: UserGroupEntity)

    @Query("SELECT id, name FROM userGroupList INNER JOIN groupList ON userGroupList.groupId = groupList.id WHERE userGroupList.userId LIKE :userId")
    fun getGroup(userId: String): GroupEntity

}
