package com.testdeymervilla.network.data

import com.testdeymervilla.network.dto.LocalityDTO
import com.testdeymervilla.network.dto.SchemaDTO
import com.testdeymervilla.network.dto.UserDTO
import com.testdeymervilla.network.dto.request.LoginRequestDTO

val dummyLocalityDTOList = listOf(
    LocalityDTO(
        cityCode = "NY",
        fullName = "New York",
        name = "NYC",
        postalCode = "10001",
        amount = 10.5f
    ),
    LocalityDTO(
        cityCode = "LA",
        fullName = "Los Angeles",
        name = "LA",
        postalCode = "90001", amount = 15.0f
    )
)

val dummySchemaDTOList = listOf(
    SchemaDTO(
        tableName = "Users",
        primaryKey = "id",
        queryCreation = "CREATE...",
        batchSize = 100,
        filter = null,
        error = null,
        fieldsNumber = 5,
        appMethod = "GET",
        updatedAt = "2024-01-01"
    )
)

val dummyLoginRequestDTO = LoginRequestDTO(username = "admin", password = "123")

val dummyExpectedUserDto = UserDTO(username = "admin", identification = "ID123", fullName = "Administrator")

val dummyWrongLoginRequestDto = LoginRequestDTO(username = "wrong", password = "wrong")

val dummyException = RuntimeException("401 Unauthorized")