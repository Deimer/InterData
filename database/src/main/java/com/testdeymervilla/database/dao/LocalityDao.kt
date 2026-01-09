package com.testdeymervilla.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testdeymervilla.database.constants.DatabaseConstants.Columns
import com.testdeymervilla.database.constants.DatabaseConstants.Tables.LOCALITY_TABLE
import com.testdeymervilla.database.entities.LocalityEntity

@Dao
interface LocalityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<LocalityEntity>): List<Long>

    @Query("SELECT * FROM $LOCALITY_TABLE ORDER BY ${Columns.ID} DESC")
    suspend fun fetch(): List<LocalityEntity>

    @Query("SELECT * FROM $LOCALITY_TABLE WHERE ${Columns.ID} = :localityId")
    suspend fun fetchById(localityId: Int): LocalityEntity

    @Query("DELETE FROM $LOCALITY_TABLE")
    suspend fun clear(): Int

    @Query("DELETE FROM $LOCALITY_TABLE WHERE ${Columns.ID} = :localityId")
    suspend fun clearById(localityId: Int): Int
}