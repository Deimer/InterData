package com.testdeymervilla.datasource.remote.user

import com.testdeymervilla.datasource.data.dummyException
import com.testdeymervilla.datasource.data.dummyExpectedUserDto
import com.testdeymervilla.datasource.data.dummyLoginRequestDTO
import com.testdeymervilla.datasource.data.dummyWrongLoginRequestDto
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
class UserRemoteDataSourceTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userRemoteDataSource = UserRemoteDataSource(mockApiService)
    }

    @Test
    fun `getVersion should return version string from API`() = runTest {
        val expectedVersion = "1.0.5"
        `when`(mockApiService.getAppVersion()).thenReturn(expectedVersion)
        val result = userRemoteDataSource.getVersion()
        assertEquals(expectedVersion, result)
        verify(mockApiService).getAppVersion()
    }

    @Test
    fun `getVersion should return empty string when API returns null`() = runTest {
        `when`(mockApiService.getAppVersion()).thenReturn(null)
        val result = userRemoteDataSource.getVersion()
        assertEquals("", result)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `login should return UserDTO on success`() = runTest {
        val request = dummyLoginRequestDTO
        val expectedUser = dummyExpectedUserDto
        `when`(mockApiService.login(request)).thenReturn(expectedUser)
        val result = userRemoteDataSource.login(request.username, request.password)
        assertEquals(expectedUser.username, result.username)
        assertEquals(expectedUser.identification, result.identification)
        assertEquals(expectedUser.fullName, result.fullName)
        verify(mockApiService).login(request)
    }

    @Test
    fun `login should throw exception when API fails`() = runTest {
        val request = dummyWrongLoginRequestDto
        val expectedException = dummyException
        `when`(mockApiService.login(request)).thenThrow(expectedException)
        try {
            userRemoteDataSource.login(request.username, request.password)
            fail("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals(expectedException.message, e.message)
        }
    }
}