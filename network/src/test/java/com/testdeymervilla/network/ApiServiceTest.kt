package com.testdeymervilla.network

import com.testdeymervilla.network.api.ApiService
import com.testdeymervilla.network.data.dummyException
import com.testdeymervilla.network.data.dummyExpectedUserDto
import com.testdeymervilla.network.data.dummyLocalityDTOList
import com.testdeymervilla.network.data.dummyLoginRequestDTO
import com.testdeymervilla.network.data.dummySchemaDTOList
import com.testdeymervilla.network.data.dummyWrongLoginRequestDto
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ApiServiceTest {

    @Mock
    private lateinit var mockApiService: ApiService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getAppVersion returns version string`() = runTest {
        val expectedVersion = "1.0.5"
        `when`(mockApiService.getAppVersion()).thenReturn(expectedVersion)
        val response = mockApiService.getAppVersion()
        assertEquals(expectedVersion, response)
    }

    @Test
    fun `getLocalities returns list of localities`() = runTest {
        `when`(mockApiService.getLocalities()).thenReturn(dummyLocalityDTOList)
        val response = mockApiService.getLocalities()
        assertEquals(2, response.size)
        assertEquals("New York", response[0].fullName)
        assertEquals("LA", response[1].name)
    }

    @Test
    fun `getSchemas returns schema list`() = runTest {
        `when`(mockApiService.getSchemas()).thenReturn(dummySchemaDTOList)
        val response = mockApiService.getSchemas()
        assertEquals(1, response.size)
        assertEquals("Users", response[0].tableName)
        assertEquals(100, response[0].batchSize)
    }

    @Test
    fun `login returns user data on success`() = runTest {
        `when`(mockApiService.login(dummyLoginRequestDTO)).thenReturn(dummyExpectedUserDto)
        val response = mockApiService.login(dummyLoginRequestDTO)
        assertEquals("admin", response.username)
        assertEquals("Administrator", response.fullName)
    }

    @Test
    fun `login handles authentication error`() = runTest {
        `when`(mockApiService.login(dummyWrongLoginRequestDto)).thenThrow(dummyException)
        try {
            mockApiService.login(dummyWrongLoginRequestDto)
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertEquals("401 Unauthorized", e.message)
        }
    }

    @Test
    fun `getLocalities returns empty list when no data`() = runTest {
        `when`(mockApiService.getLocalities()).thenReturn(emptyList())
        val response = mockApiService.getLocalities()
        assertTrue(response.isEmpty())
    }
}