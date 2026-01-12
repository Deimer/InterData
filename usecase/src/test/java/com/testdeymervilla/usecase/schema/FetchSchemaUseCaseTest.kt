package com.testdeymervilla.usecase.schema

import com.testdeymervilla.repository.domain.SchemaDomain
import com.testdeymervilla.repository.repositories.schema.ISchemaRepository
import com.testdeymervilla.usecase.data.dummySchemaDomain
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class FetchSchemaUseCaseTest {

    @Mock
    private lateinit var mockSchemaRepository: ISchemaRepository

    private lateinit var fetchSchemaUseCase: FetchSchemaUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchSchemaUseCase = FetchSchemaUseCase(mockSchemaRepository)
    }

    @Test
    fun `invoke should emit success when repository returns schema by id`() = runTest {
        val schemaId = 1
        val expectedSchema = dummySchemaDomain
        val successFlow = flowOf(Result.success(expectedSchema))
        `when`(mockSchemaRepository.getSchema(schemaId)).thenReturn(successFlow)
        val results = mutableListOf<Result<SchemaDomain>>()
        fetchSchemaUseCase(schemaId).toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedSchema, results.first().getOrNull())
        verify(mockSchemaRepository).getSchema(schemaId)
    }

    @Test
    fun `invoke should emit failure when repository cannot find schema`() = runTest {
        val schemaId = 99
        val exception = IllegalArgumentException("Schema not found")
        val errorFlow = flowOf(Result.failure<SchemaDomain>(exception))
        `when`(mockSchemaRepository.getSchema(schemaId)).thenReturn(errorFlow)
        val results = mutableListOf<Result<SchemaDomain>>()
        fetchSchemaUseCase(schemaId).toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockSchemaRepository).getSchema(schemaId)
    }

    @Test
    fun `invoke should handle repository exceptions`() = runTest {
        val schemaId = 1
        val exception = RuntimeException("Database error")
        `when`(mockSchemaRepository.getSchema(schemaId))
            .thenReturn(flowOf(Result.failure(exception)))
        val results = mutableListOf<Result<SchemaDomain>>()
        fetchSchemaUseCase(schemaId).toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
    }
}