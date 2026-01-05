package com.testdeymervilla.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.testdeymervilla.database.constants.DatabaseConstants.Columns
import com.testdeymervilla.database.constants.DatabaseConstants.Tables

@Entity(tableName = Tables.USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    val id: Int,
    @ColumnInfo(name = Columns.USERNAME)
    val user: String,
    @ColumnInfo(name = Columns.IDENTIFICATION)
    val identification:  String,
    @ColumnInfo(name = Columns.FULL_NAME)
    val fullName:  String
)