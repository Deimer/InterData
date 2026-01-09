package com.testdeymervilla.interdata.utils

import com.testdeymervilla.repository.domain.LocalityDomain
import com.testdeymervilla.repository.domain.SchemaDomain
import com.testdeymervilla.repository.domain.UserDomain

val mockUser = UserDomain(
    id = 1,
    username = "test.user",
    identification = "123456789",
    fullName = "Test User",
    shortName = "Test"
)

val mockSchemas = listOf(
    SchemaDomain(1, "Users", 10, "2023-10-01", "id", 100),
    SchemaDomain(2, "Products", 5, "2023-10-02", "code", 50),
    SchemaDomain(3, "Orders", 8, "2023-10-03", "order_id", 200)
)

val mockLocalities = listOf(
    LocalityDomain(
        id = 1,
        name = "Chapinero",
        fullName = "Chapinero Alto",
        cityAbbreviation = "BOG",
        zipCode = "110231",
        pickupValue = "$ 15.000"
    ),
    LocalityDomain(
        id = 2,
        name = "Poblado",
        fullName = "El Poblado",
        cityAbbreviation = "MDE",
        zipCode = "050021",
        pickupValue = "$ 18.500"
    ),
    LocalityDomain(
        id = 3,
        name = "Bocagrande",
        fullName = "Bocagrande Turístico",
        cityAbbreviation = "CTG",
        zipCode = "130001",
        pickupValue = "$ 22.000"
    ),
    LocalityDomain(
        id = 4,
        name = "Pance",
        fullName = "Sector Pance",
        cityAbbreviation = "CLO",
        zipCode = "760032",
        pickupValue = "$ 12.000"
    ),
    LocalityDomain(
        id = 5,
        name = "Cabecera",
        fullName = "Cabecera del Llano",
        cityAbbreviation = "BUC",
        zipCode = "680003",
        pickupValue = "$ 10.000"
    )
)