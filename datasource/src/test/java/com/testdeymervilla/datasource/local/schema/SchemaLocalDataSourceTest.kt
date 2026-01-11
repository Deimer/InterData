package com.testdeymervilla.datasource.local.schema

import com.testdeymervilla.database.dao.SchemaDao
import com.testdeymervilla.datasource.data.dummySchemaEntityList
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class SchemaLocalDataSourceTest {

    @Mock
    private lateinit var mockSchemaDao: SchemaDao

    private lateinit var schemaLocalDataSource: SchemaLocalDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        schemaLocalDataSource = SchemaLocalDataSource(mockSchemaDao)
    }

    @Test
    fun `insert should return true when all schemas are inserted successfully`() = runTest {
        val schemas = dummySchemaEntityList
        `when`(mockSchemaDao.insert(schemas)).thenReturn(listOf(1L, 2L))
        val result = schemaLocalDataSource.insert(schemas)
        assertTrue(result)
        verify(mockSchemaDao).insert(schemas)
    }

    @Test
    fun `insert should return false when insertion returns empty list`() = runTest {
        val schemas = dummySchemaEntityList
        `when`(mockSchemaDao.insert(schemas)).thenReturn(emptyList())
        val result = schemaLocalDataSource.insert(schemas)
        assertFalse(result)
    }

    @Test
    fun `fetch should return all schemas from dao`() = runTest {
        val expectedSchemas = dummySchemaEntityList
        `when`(mockSchemaDao.fetch()).thenReturn(expectedSchemas)
        val result = schemaLocalDataSource.fetch()
        assertEquals(expectedSchemas, result)
        verify(mockSchemaDao).fetch()
    }

    @Test
    fun `fetchById should return specific schema`() = runTest {
        val schemaId = 1
        val expectedSchema = dummySchemaEntityList[0]
        `when`(mockSchemaDao.fetchById(schemaId)).thenReturn(expectedSchema)
        val result = schemaLocalDataSource.fetchById(schemaId)
        assertEquals(expectedSchema, result)
        verify(mockSchemaDao).fetchById(schemaId)
    }

    @Test
    fun `clear should return true when rows are deleted`() = runTest {
        `when`(mockSchemaDao.clear()).thenReturn(5)
        val result = schemaLocalDataSource.clear()
        assertTrue(result)
        verify(mockSchemaDao).clear()
    }

    @Test
    fun `clearById should return true when one row is deleted`() = runTest {
        val schemaId = 1
        `when`(mockSchemaDao.clearById(schemaId)).thenReturn(1)
        val result = schemaLocalDataSource.clearById(schemaId)
        assertTrue(result)
        verify(mockSchemaDao).clearById(schemaId)
    }

    @Test
    fun `clearById should return false when no row is deleted`() = runTest {
        val schemaId = 99
        `when`(mockSchemaDao.clearById(schemaId)).thenReturn(0)
        val result = schemaLocalDataSource.clearById(schemaId)
        assertFalse(result)
    }
}