package com.example.fragmentstest.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fragmentstest.models.GroupEntity
import com.example.fragmentstest.models.User
import com.example.fragmentstest.models.UserEntity

@Dao
interface GroupDao {

    @Query("SELECT * FROM groupList")
    fun getGroups(): List<GroupEntity>

    @Insert
    fun createGroup(group: GroupEntity)

    @Delete
    fun removeGroup(group: GroupEntity)

    @Query("SELECT name FROM groupList where id=:groupId")
    fun getGroupName(groupId: Int): String
    
}
