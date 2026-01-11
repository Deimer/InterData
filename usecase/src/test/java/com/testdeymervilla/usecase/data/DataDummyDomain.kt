package com.testdeymervilla.usecase.data

import com.testdeymervilla.repository.domain.LocalityDomain
import com.testdeymervilla.repository.domain.SchemaDomain
import com.testdeymervilla.repository.domain.UserDomain

val dummyUserDomain = UserDomain(
    id = 1,
    username = "admin",
    identification = "123456789",
    fullName = "Deymer Villa",
    shortName = "Deymer"
)

val dummyUserDomainUpdated = UserDomain(
    id = 1,
    username = "admin_updated",
    identification = "123456789",
    fullName = "Deymer Villa Editado",
    shortName = "Deymer"
)

val dummyUserDomainList = listOf(
    dummyUserDomain,
    UserDomain(
        id = 2,
        username = "jdoe",
        identification = "987654321",
        fullName = "John Doe",
        shortName = "John"
    )
)

val dummySchemaDomain = SchemaDomain(
    id = 1,
    tableName = "USERS_TABLE",
    fieldsCount = 12,
    updatedAt = "Saturday 10, January, 2026 - 12:42",
    primaryKey = "ID",
    batchSize = 500
)

val dummySchemaDomainList = listOf(
    dummySchemaDomain,
    SchemaDomain(
        id = 2,
        tableName = "ORDERS_TABLE",
        fieldsCount = 8,
        updatedAt = "Sunday 11, January, 2026 - 09:15",
        primaryKey = "ORDER_ID",
        batchSize = 1000
    ),
    SchemaDomain(
        id = 3,
        tableName = "PRODUCTS_TABLE",
        fieldsCount = 15,
        updatedAt = "Monday 12, January, 2026 - 14:30",
        primaryKey = "SKU",
        batchSize = 250
    )
)

val dummyLocalityDomain = LocalityDomain(
    id = 1,
    name = "Medellín",
    fullName = "Antioquia, Medellín",
    cityAbbreviation = "MDE",
    zipCode = "050001",
    pickupValue = "$ 1.500"
)

val dummyLocalityDomainList = listOf(
    dummyLocalityDomain,
    LocalityDomain(
        id = 2,
        name = "Bogotá",
        fullName = "Cundinamarca, Bogotá",
        cityAbbreviation = "BOG",
        zipCode = "010001",
        pickupValue = "$ 2.000"
    )
)