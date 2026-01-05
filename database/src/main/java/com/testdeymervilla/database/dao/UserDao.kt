package com.testdeymervilla.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.testdeymervilla.database.constants.DatabaseConstants.Tables.USER_TABLE
import com.testdeymervilla.database.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Query("DELETE FROM $USER_TABLE")
    suspend fun clear()

    @Query("SELECT * FROM $USER_TABLE LIMIT 1")
    suspend fun fetch(): UserEntity
}