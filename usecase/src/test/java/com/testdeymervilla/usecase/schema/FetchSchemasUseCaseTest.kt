package com.testdeymervilla.usecase.schema

import com.testdeymervilla.repository.domain.SchemaDomain
import com.testdeymervilla.repository.repositories.schema.ISchemaRepository
import com.testdeymervilla.usecase.data.dummySchemaDomainList
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
class FetchSchemasUseCaseTest {

    @Mock
    private lateinit var mockSchemaRepository: ISchemaRepository

    private lateinit var fetchSchemasUseCase: FetchSchemasUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchSchemasUseCase = FetchSchemasUseCase(mockSchemaRepository)
    }

    @Test
    fun `invoke should emit success result when repository returns schema list`() = runTest {
        val expectedSchemas = dummySchemaDomainList
        val successFlow = flowOf(Result.success(expectedSchemas))
        `when`(mockSchemaRepository.getSchemas()).thenReturn(successFlow)
        val results = mutableListOf<Result<List<SchemaDomain>>>()
        fetchSchemasUseCase().toList(results)
        assertEquals(1, results.size)
        val result = results.first()
        assertTrue(result.isSuccess)
        assertEquals(expectedSchemas, result.getOrNull())
        verify(mockSchemaRepository).getSchemas()
    }

    @Test
    fun `invoke should emit success with empty list when no schemas are found`() = runTest {
        val successFlow = flowOf(Result.success(emptyList<SchemaDomain>()))
        `when`(mockSchemaRepository.getSchemas()).thenReturn(successFlow)
        val results = mutableListOf<Result<List<SchemaDomain>>>()
        fetchSchemasUseCase().toList(results)
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `invoke should emit failure when repository throws exception`() = runTest {
        val exception = Exception("Error fetching schemas")
        val errorFlow = flowOf(Result.failure<List<SchemaDomain>>(exception))
        `when`(mockSchemaRepository.getSchemas()).thenReturn(errorFlow)
        val results = mutableListOf<Result<List<SchemaDomain>>>()
        fetchSchemasUseCase().toList(results)
        assertEquals(1, results.size)
        val result = results.first()
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(mockSchemaRepository).getSchemas()
    }
}