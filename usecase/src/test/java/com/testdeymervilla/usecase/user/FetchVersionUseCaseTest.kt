package com.testdeymervilla.usecase.user

import com.testdeymervilla.repository.repositories.user.IUserRepository
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
class FetchVersionUseCaseTest {

    @Mock
    private lateinit var mockUserRepository: IUserRepository

    private lateinit var fetchVersionUseCase: FetchVersionUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchVersionUseCase = FetchVersionUseCase(mockUserRepository)
    }

    @Test
    fun `invoke should emit version string when repository is successful`() = runTest {
        val expectedVersion = "1.0.5 (Build 42)"
        `when`(mockUserRepository.getVersion()).thenReturn(flowOf(Result.success(expectedVersion)))
        val results = mutableListOf<Result<String>>()
        fetchVersionUseCase().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedVersion, results.first().getOrNull())
        verify(mockUserRepository).getVersion()
    }

    @Test
    fun `invoke should emit failure when repository fails to fetch version`() = runTest {
        val exception = Exception("Service Unavailable")
        `when`(mockUserRepository.getVersion()).thenReturn(flowOf(Result.failure(exception)))
        val results = mutableListOf<Result<String>>()
        fetchVersionUseCase().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockUserRepository).getVersion()
    }

    @Test
    fun `invoke should handle empty string version from repository`() = runTest {
        val emptyVersion = ""
        `when`(mockUserRepository.getVersion()).thenReturn(flowOf(Result.success(emptyVersion)))
        val results = mutableListOf<Result<String>>()
        fetchVersionUseCase().toList(results)
        assertEquals(1, results.size)
        assertEquals("", results.first().getOrNull())
    }

    @Test
    fun `invoke should emit failure for connection timeout`() = runTest {
        val timeoutException = java.net.SocketTimeoutException("Timeout")
        `when`(mockUserRepository.getVersion()).thenReturn(flowOf(Result.failure(timeoutException)))
        val results = mutableListOf<Result<String>>()
        fetchVersionUseCase().toList(results)
        assertTrue(results.first().isFailure)
        assertTrue(results.first().exceptionOrNull() is java.net.SocketTimeoutException)
    }
}