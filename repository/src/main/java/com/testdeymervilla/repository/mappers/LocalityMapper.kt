package com.testdeymervilla.repository.mappers

import com.testdeymervilla.database.entities.LocalityEntity
import com.testdeymervilla.network.dto.LocalityDTO
import com.testdeymervilla.repository.domain.LocalityDomain
import com.testdeymervilla.repository.utils.toCopFormat

fun LocalityDTO.toEntity(): LocalityEntity {
    val dto = this
    return LocalityEntity(
        id = 0,
        fullName = dto.fullName.orEmpty(),
        name = dto.name.orEmpty(),
        cityAbbreviation = dto.cityCode.orEmpty(),
        zipCode = dto.postalCode.orEmpty(),
        pickupValue = dto.amount ?: 0f
    )
}

fun LocalityEntity.toDomain(): LocalityDomain {
    val entity = this
    return LocalityDomain(
        id = entity.id,
        name = entity.name,
        fullName = entity.fullName,
        cityAbbreviation = entity.cityAbbreviation,
        zipCode = entity.zipCode,
        pickupValue = entity.pickupValue.toCopFormat()
    )
}