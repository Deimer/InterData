package com.testdeymervilla.usecase.schema

import com.testdeymervilla.repository.repositories.schema.ISchemaRepository
import javax.inject.Inject

class FetchSchemasUseCase @Inject constructor(
    private val schemaRepository: ISchemaRepository
) {

    operator fun invoke() =
        schemaRepository.getSchemas()
}