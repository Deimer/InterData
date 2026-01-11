package com.testdeymervilla.repository.repositories

import com.testdeymervilla.datasource.local.schema.ISchemaLocalDataSource
import com.testdeymervilla.datasource.remote.schema.ISchemaRemoteDataSource
import com.testdeymervilla.repository.data.dummySchemaDTOList
import com.testdeymervilla.repository.data.dummySchemaEntityList
import com.testdeymervilla.repository.domain.SchemaDomain
import com.testdeymervilla.repository.repositories.schema.SchemaRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@ExperimentalCoroutinesApi
class SchemaRepositoryTest {

    @Mock
    private lateinit var mockSchemaLocalDataSource: ISchemaLocalDataSource

    @Mock
    private lateinit var mockSchemaRemoteDataSource: ISchemaRemoteDataSource

    private lateinit var schemaRepository: SchemaRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        schemaRepository = SchemaRepository(mockSchemaLocalDataSource, mockSchemaRemoteDataSource)
    }

    @Test
    fun `getSchemas emits local data when available`() = runTest {
        val localSchemas = dummySchemaEntityList
        `when`(mockSchemaLocalDataSource.fetch()).thenReturn(localSchemas)
        val results = mutableListOf<Result<List<SchemaDomain>>>()
        schemaRepository.getSchemas().toList(results)
        val firstResult = results.first()
        assertTrue("The flow failed with the exception: ${firstResult.exceptionOrNull()}", firstResult.isSuccess)
        assertEquals(localSchemas.size, firstResult.getOrNull()?.size)
        verify(mockSchemaLocalDataSource).fetch()
        verifyNoInteractions(mockSchemaRemoteDataSource)
    }

    @Test
    fun `getSchemas fetches from remote when local is empty`() = runTest {
        val remoteSchemas = dummySchemaDTOList
        val localSchemasAfterInsert = dummySchemaEntityList
        `when`(mockSchemaLocalDataSource.fetch())
            .thenReturn(emptyList())
            .thenReturn(localSchemasAfterInsert)
        `when`(mockSchemaRemoteDataSource.fetch()).thenReturn(remoteSchemas)
        `when`(mockSchemaLocalDataSource.insert(any())).thenReturn(true)
        val results = mutableListOf<Result<List<SchemaDomain>>>()
        schemaRepository.getSchemas().toList(results)
        assertTrue(results.first().isSuccess)
        verify(mockSchemaRemoteDataSource).fetch()
        verify(mockSchemaLocalDataSource).insert(any())
    }

    @Test
    fun `getSchema emits specific schema domain`() = runTest {
        val schemaId = 1
        val entity = dummySchemaEntityList[0]
        `when`(mockSchemaLocalDataSource.fetchById(schemaId)).thenReturn(entity)
        val results = mutableListOf<Result<SchemaDomain>>()
        schemaRepository.getSchema(schemaId).toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(entity.tableName, results.first().getOrNull()?.tableName)
    }

    @Test
    fun `getSchema emits failure when exception occurs`() = runTest {
        val exception = RuntimeException("DB Error")
        `when`(mockSchemaLocalDataSource.fetchById(anyInt())).thenThrow(exception)
        val results = mutableListOf<Result<SchemaDomain>>()
        schemaRepository.getSchema(1).toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
    }

    @Test
    fun `clearSchema emits true when deleted successfully`() = runTest {
        val schemaId = 1
        `when`(mockSchemaLocalDataSource.clearById(schemaId)).thenReturn(true)
        val results = mutableListOf<Result<Boolean>>()
        schemaRepository.clearSchema(schemaId).toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(true, results.first().getOrNull())
    }

    @Test
    fun `clearSchemas emits true when table is cleared`() = runTest {
        `when`(mockSchemaLocalDataSource.clear()).thenReturn(true)
        val results = mutableListOf<Result<Boolean>>()
        schemaRepository.clearSchemas().toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(true, results.first().getOrNull())
    }
}