package com.lib.net.local.dao

import androidx.room.*
import com.lib.net.local.entity.UserTestRoom

/**
 */
@Dao
interface UserTestRoomDao {

    @Insert
    fun insertUserTestRoom(userTestRoom: UserTestRoom): Long

    @Update
    fun updateUserTestRoom(newUserTestRoom: UserTestRoom)

    @Query("SELECT * FROM USERTESTROOM ORDER BY id DESC")
    fun loadAllUserTestRooms(): List<UserTestRoom>

    @Query("SELECT * FROM USERTESTROOM WHERE AGE > :age")
    fun loadUserTestRoomOlderThan(age: Int): List<UserTestRoom>

    @Delete
    fun deleteUserTestRoom(deleteUserTestRoom: UserTestRoom): Int

    @Query("DELETE FROM USERTESTROOM WHERE lastName = :lastName")
    fun deleteUserTestRoomByLastName(lastName: String): Int
}