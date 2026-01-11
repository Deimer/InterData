package com.testdeymervilla.repository.mappers

import com.testdeymervilla.network.dto.SchemaDTO
import com.testdeymervilla.repository.data.dummySchemaDTOList
import com.testdeymervilla.repository.data.dummySchemaEntityList
import com.testdeymervilla.repository.utils.toHumanDate
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

class SchemaMapperTest {

    @Test
    fun `test SchemaDTO toEntity maps correctly`() {
        val dto = dummySchemaDTOList.first()
        val entity = dto.toEntity()
        assertEquals(dto.tableName.orEmpty(), entity.tableName)
        assertEquals(dto.primaryKey.orEmpty(), entity.primaryKey)
        assertEquals(dto.queryCreation.orEmpty(), entity.content)
        assertEquals(dto.batchSize ?: 0, entity.batchSize)
    }

    @Test
    fun `test SchemaEntity toDomain maps correctly`() {
        val entity = dummySchemaEntityList.first()
        val domain = entity.toDomain()
        assertEquals(entity.id, domain.id)
        assertEquals(entity.tableName, domain.tableName)
        assertEquals(entity.fieldsCount, domain.fieldsCount)
        val expectedDate = entity.updatedAt.toHumanDate()
        assertEquals(expectedDate, domain.updatedAt)
    }

    @Test
    fun `test SchemaDTO toEntity with null numbers returns zero`() {
        val dto = SchemaDTO(
            tableName = "Test",
            primaryKey = null,
            queryCreation = null,
            batchSize = null,
            fieldsNumber = null,
            filter = null,
            error = null,
            appMethod = null,
            updatedAt = null
        )
        val entity = dto.toEntity()
        assertEquals(0, entity.batchSize)
        assertEquals(0, entity.fieldsCount)
        assertEquals("", entity.primaryKey)
    }

    @Test
    fun `test SchemaEntity toDomain formats date correctly using extension`() {
        val entity = dummySchemaEntityList.first().copy(updatedAt = "2026-01-10T12:00:00.556Z")
        val domain = entity.toDomain()
        assertNotEquals(entity.updatedAt, domain.updatedAt)
        assertTrue(domain.updatedAt.contains("2026"))
    }
}