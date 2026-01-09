package com.testdeymervilla.repository.mappers

import com.testdeymervilla.database.entities.SchemaEntity
import com.testdeymervilla.network.dto.SchemaDTO
import com.testdeymervilla.repository.domain.SchemaDomain
import com.testdeymervilla.repository.utils.orZero
import com.testdeymervilla.repository.utils.toHumanDate

fun SchemaDTO.toEntity(): SchemaEntity {
    val dto = this
    return SchemaEntity(
        id = 0,
        tableName = dto.tableName.orEmpty(),
        content = dto.queryCreation.orEmpty(),
        primaryKey = dto.primaryKey.orEmpty(),
        batchSize = dto.batchSize.orZero(),
        filter = dto.filter.orEmpty(),
        fieldsCount = dto.fieldsNumber.orZero(),
        updatedAt = dto.updatedAt.orEmpty()
    )
}

fun SchemaEntity.toDomain(): SchemaDomain {
    val entity = this
    return SchemaDomain(
        id = entity.id,
        tableName = entity.tableName,
        fieldsCount = entity.fieldsCount,
        updatedAt = entity.updatedAt.toHumanDate(),
        primaryKey = entity.primaryKey,
        batchSize = entity.batchSize
    )
}