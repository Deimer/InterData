package com.testdeymervilla.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testdeymervilla.database.constants.DatabaseConstants.Columns
import com.testdeymervilla.database.constants.DatabaseConstants.Tables.SCHEMA_TABLE
import com.testdeymervilla.database.entities.SchemaEntity

@Dao
interface SchemaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<SchemaEntity>)

    @Query("SELECT * FROM $SCHEMA_TABLE ORDER BY ${Columns.ID} DESC")
    suspend fun fetch(): List<SchemaEntity>

    @Query("SELECT * FROM $SCHEMA_TABLE WHERE ${Columns.ID} = :schemaId")
    suspend fun fetchById(schemaId: Int): SchemaEntity

    @Query("DELETE FROM $SCHEMA_TABLE")
    suspend fun clear()

    @Query("DELETE FROM $SCHEMA_TABLE WHERE ${Columns.ID} = :schemaId")
    suspend fun clearById(schemaId: Int)
}