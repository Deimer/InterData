package com.testdeymervilla.datasource.remote.schema

import com.testdeymervilla.datasource.data.dummySchemaDTOList
import com.testdeymervilla.network.api.ApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class SchemaRemoteDataSourceTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var schemaRemoteDataSource: SchemaRemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        schemaRemoteDataSource = SchemaRemoteDataSource(mockApiService)
    }

    @Test
    fun `fetch should return list of SchemaDTO from API`() = runTest {
        val expectedSchemas = dummySchemaDTOList
        `when`(mockApiService.getSchemas()).thenReturn(expectedSchemas)
        val result = schemaRemoteDataSource.fetch()
        assertEquals(expectedSchemas.size, result.size)
        assertEquals(expectedSchemas[0].tableName, result[0].tableName)
        verify(mockApiService).getSchemas()
    }

    @Test
    fun `fetch should return empty list when API has no data`() = runTest {
        `when`(mockApiService.getSchemas()).thenReturn(emptyList())
        val result = schemaRemoteDataSource.fetch()
        assertTrue(result.isEmpty())
        verify(mockApiService).getSchemas()
    }

    @Test
    fun `fetch should throw exception when API fails`() = runTest {
        val expectedException = RuntimeException("Server Error")
        `when`(mockApiService.getSchemas()).thenThrow(expectedException)
        try {
            schemaRemoteDataSource.fetch()
            fail("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals(expectedException.message, e.message)
        }
    }
}