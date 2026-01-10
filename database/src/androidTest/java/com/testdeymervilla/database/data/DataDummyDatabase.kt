package com.testdeymervilla.database.data

import com.testdeymervilla.database.entities.LocalityEntity
import com.testdeymervilla.database.entities.SchemaEntity
import com.testdeymervilla.database.entities.UserEntity

val dummyLocalityEntityList = listOf(
    LocalityEntity(
        id = 1,
        fullName = "Full Name 1",
        name = "Name 1",
        cityAbbreviation = "ABC",
        zipCode = "12345",
        pickupValue = 10.0f
    ),
    LocalityEntity(
        id = 2,
        fullName = "Full Name 2",
        name = "Name 2",
        cityAbbreviation = "DEF",
        zipCode = "67890",
        pickupValue = 20.0f
    )
)

val dummyLocalityEntity = LocalityEntity(
    10,
    fullName = "Target",
    name = "T",
    cityAbbreviation = "TGT",
    zipCode = "000",
    pickupValue = 5.0f
)

val dummySchemaEntityList = listOf(
    SchemaEntity(
        id = 1,
        tableName = "Table1",
        content = "Content",
        primaryKey = "pk",
        batchSize = 50,
        filter = "filter",
        fieldsCount = 10,
        updatedAt = "2024-01-01"
    )
)

val dummyUserEntity = UserEntity(
    id = 1,
    user = "admin",
    identification = "12345",
    fullName = "Admin User"
)

val dummyUserEntityUpdated = UserEntity(
    id = 1,
    user = "admin",
    identification = "12345",
    fullName = "Updated Name"
)