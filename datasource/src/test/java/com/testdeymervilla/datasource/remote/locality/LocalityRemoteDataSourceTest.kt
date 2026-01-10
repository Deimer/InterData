package com.testdeymervilla.datasource.remote.locality

import com.testdeymervilla.datasource.data.dummyLocalityDTOList
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
class LocalityRemoteDataSourceTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var localityRemoteDataSource: LocalityRemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        localityRemoteDataSource = LocalityRemoteDataSource(mockApiService)
    }

    @Test
    fun `fetch should return list of LocalityDTO from API`() = runTest {
        val expectedLocalities = dummyLocalityDTOList
        `when`(mockApiService.getLocalities()).thenReturn(expectedLocalities)
        val result = localityRemoteDataSource.fetch()
        assertEquals(expectedLocalities.size, result.size)
        assertEquals(expectedLocalities[0].cityCode, result[0].cityCode)
        assertEquals(expectedLocalities[1].fullName, result[1].fullName)
        verify(mockApiService).getLocalities()
    }

    @Test
    fun `fetch should return empty list when API returns no results`() = runTest {
        `when`(mockApiService.getLocalities()).thenReturn(emptyList())
        val result = localityRemoteDataSource.fetch()
        assertTrue(result.isEmpty())
        verify(mockApiService).getLocalities()
    }

    @Test
    fun `fetch should throw exception when network call fails`() = runTest {
        val expectedException = RuntimeException("Connection timeout")
        `when`(mockApiService.getLocalities()).thenThrow(expectedException)
        try {
            localityRemoteDataSource.fetch()
            fail("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals("Connection timeout", e.message)
        }
    }
}