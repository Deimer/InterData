package com.testdeymervilla.repository.mappers

import com.testdeymervilla.database.entities.UserEntity
import com.testdeymervilla.network.dto.UserDTO
import com.testdeymervilla.repository.domain.UserDomain
import com.testdeymervilla.repository.utils.orZero

fun UserDTO.toEntity(): UserEntity {
    val dto = this
    return UserEntity(
        id = 0,
        user = dto.username.orEmpty(),
        identification = dto.identification.orEmpty(),
        fullName = dto.fullName.orEmpty()
    )
}

fun UserEntity?.toDomain(): UserDomain {
    val entity = this
    return UserDomain(
        id = entity?.id.orZero(),
        username = entity?.user.orEmpty(),
        identification = entity?.identification.orEmpty(),
        fullName = entity?.fullName.orEmpty()
    )
}