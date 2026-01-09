package com.testdeymervilla.usecase.schema

import com.testdeymervilla.repository.repositories.schema.ISchemaRepository
import javax.inject.Inject

class FetchSchemaUseCase @Inject constructor(
    private val schemaRepository: ISchemaRepository
) {

    operator fun invoke(
        schemaId: Int
    ) = schemaRepository.getSchema(
        schemaId
    )
}