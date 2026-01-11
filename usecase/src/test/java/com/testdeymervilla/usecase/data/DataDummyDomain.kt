package com.testdeymervilla.usecase.data

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

fun generateDummyUserDomain(
    id: Int = 1,
    username: String = "user_$id",
    fullName: String = "Full Name $id"
) = UserDomain(
    id = id,
    username = username,
    identification = "ID_$id",
    fullName = fullName,
    shortName = fullName.split(" ").first()
)