package com.testdeymervilla.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.testdeymervilla.database.constants.DatabaseConstants.Columns
import com.testdeymervilla.database.constants.DatabaseConstants.Tables

@Entity(tableName = Tables.SCHEMA_TABLE)
data class SchemaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    val id: Int,
    @ColumnInfo(name = Columns.TABLE_NAME)
    val tableName: String,
    @ColumnInfo(name = Columns.CONTENT)
    val content: String,
    @ColumnInfo(name = Columns.PRIMARY_KEY)
    val primaryKey: String,
    @ColumnInfo(name = Columns.BATCH_SIZE)
    val batchSize: Int,
    @ColumnInfo(name = Columns.FILTER)
    val filter: String,
    @ColumnInfo(name = Columns.FIELDS_COUNT)
    val fieldsCount: Int,
    @ColumnInfo(name = Columns.UPDATE_DATE)
    val updatedAt: String
)