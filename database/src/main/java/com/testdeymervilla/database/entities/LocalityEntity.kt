package com.testdeymervilla.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.testdeymervilla.database.constants.DatabaseConstants.Columns
import com.testdeymervilla.database.constants.DatabaseConstants.Tables

@Entity(tableName = Tables.LOCALITY_TABLE)
data class LocalityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    val id: Int,
    @ColumnInfo(name = Columns.FULL_NAME)
    val fullName: String,
    @ColumnInfo(name = Columns.NAME)
    val name: String,
    @ColumnInfo(name = Columns.CITY_ABBREVIATION)
    val cityAbbreviation: String,
    @ColumnInfo(name = Columns.ZIP_CODE)
    val zipCode: String,
    @ColumnInfo(name = Columns.PICKUP_VALUE)
    val pickupValue: Float
)