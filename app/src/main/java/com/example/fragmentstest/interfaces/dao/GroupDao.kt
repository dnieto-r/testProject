package com.example.fragmentstest.interfaces.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fragmentstest.models.entities.GroupEntity

@Dao
interface GroupDao {

    @Query("SELECT * FROM groupList")
    fun getGroups(): List<GroupEntity>

    @Insert
    fun createGroup(group: GroupEntity)

    @Update
    fun updateGroup(group: GroupEntity)

    @Delete
    fun removeGroup(group: GroupEntity)

    @Query("SELECT name FROM groupList where id=:groupId")
    fun getGroupName(groupId: Int): String
    
}
