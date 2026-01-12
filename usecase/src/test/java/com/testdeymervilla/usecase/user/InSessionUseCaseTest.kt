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
class InSessionUseCaseTest {

    @Mock
    private lateinit var mockUserRepository: IUserRepository

    private lateinit var inSessionUseCase: InSessionUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        inSessionUseCase = InSessionUseCase(mockUserRepository)
    }

    @Test
    fun `invoke should emit true when repository confirms active session`() = runTest {
        `when`(mockUserRepository.inSession()).thenReturn(flowOf(Result.success(true)))

        val results = mutableListOf<Result<Boolean>>()
        inSessionUseCase().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().getOrNull() == true)
        verify(mockUserRepository).inSession()
    }

    @Test
    fun `invoke should emit false when repository confirms no session`() = runTest {
        `when`(mockUserRepository.inSession()).thenReturn(flowOf(Result.success(false)))
        val results = mutableListOf<Result<Boolean>>()
        inSessionUseCase().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().getOrNull() == false)
        verify(mockUserRepository).inSession()
    }

    @Test
    fun `invoke should emit failure when repository check fails`() = runTest {
        val exception = Exception("Database access error")
        `when`(mockUserRepository.inSession()).thenReturn(flowOf(Result.failure(exception)))
        val results = mutableListOf<Result<Boolean>>()
        inSessionUseCase().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
    }

    @Test
    fun `invoke should handle unexpected null result as failure`() = runTest {
        val errorFlow = flowOf(Result.failure<Boolean>(NullPointerException()))
        `when`(mockUserRepository.inSession()).thenReturn(errorFlow)
        val results = mutableListOf<Result<Boolean>>()
        inSessionUseCase().toList(results)
        assertTrue(results.first().isFailure)
        assertTrue(results.first().exceptionOrNull() is NullPointerException)
    }
}