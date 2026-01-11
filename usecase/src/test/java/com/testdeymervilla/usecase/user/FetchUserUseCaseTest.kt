package com.testdeymervilla.usecase.user

import com.testdeymervilla.repository.domain.UserDomain
import com.testdeymervilla.repository.repositories.user.IUserRepository
import com.testdeymervilla.usecase.data.dummyUserDomain
import com.testdeymervilla.usecase.data.dummyUserDomainUpdated
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
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
class FetchUserUseCaseTest {

    @Mock
    private lateinit var mockUserRepository: IUserRepository

    private lateinit var fetchUserUseCase: FetchUserUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchUserUseCase = FetchUserUseCase(mockUserRepository)
    }

    @Test
    fun `invoke should emit success result when repository returns user domain`() = runTest {
        val expectedUserDomain = dummyUserDomain
        val successFlow = flowOf(Result.success(expectedUserDomain))
        `when`(mockUserRepository.getUser()).thenReturn(successFlow)
        val results = mutableListOf<Result<UserDomain>>()
        fetchUserUseCase().toList(results)
        assertEquals(1, results.size)
        val result = results.first()
        assertTrue(result.isSuccess)
        assertEquals(expectedUserDomain, result.getOrNull())
        verify(mockUserRepository).getUser()
    }

    @Test
    fun `invoke should emit error result when repository returns failure`() = runTest {
        val exception = Exception("User not found")
        val errorFlow = flowOf(Result.failure<UserDomain>(exception))
        `when`(mockUserRepository.getUser()).thenReturn(errorFlow)
        val results = mutableListOf<Result<UserDomain>>()
        fetchUserUseCase().toList(results)
        assertEquals(1, results.size)
        val result = results.first()
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verify(mockUserRepository).getUser()
    }

    @Test
    fun `invoke should emit success when user exists`() = runTest {
        val expectedUser = dummyUserDomain
        `when`(mockUserRepository.getUser()).thenReturn(flowOf(Result.success(expectedUser)))
        val results = fetchUserUseCase().toList()
        assertEquals(1, results.size)
        assertEquals(expectedUser, results.first().getOrNull())
    }

    @Test
    fun `invoke should emit failure when repository returns an error`() = runTest {
        val exception = RuntimeException("Database error")
        `when`(mockUserRepository.getUser()).thenReturn(flowOf(Result.failure(exception)))
        val results = fetchUserUseCase().toList()
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
    }

    @Test
    fun `invoke should handle empty flow from repository`() = runTest {
        `when`(mockUserRepository.getUser()).thenReturn(emptyFlow())
        val results = fetchUserUseCase().toList()
        assertTrue(results.isEmpty())
    }

    @Test
    fun `invoke should handle multiple emissions if repository updates`() = runTest {
        val user1 = dummyUserDomain
        val user2 = dummyUserDomainUpdated
        val flow = flow {
            emit(Result.success(user1))
            emit(Result.success(user2))
        }
        `when`(mockUserRepository.getUser()).thenReturn(flow)
        val results = fetchUserUseCase().toList()
        assertEquals(2, results.size)
        assertEquals(user1, results[0].getOrNull())
        assertEquals(user2, results[1].getOrNull())
    }

    @Test
    fun `invoke should emit failure for specific IOException`() = runTest {
        val ioException = java.io.IOException("No internet")
        `when`(mockUserRepository.getUser()).thenReturn(flowOf(Result.failure(ioException)))
        val results = fetchUserUseCase().toList()
        val exception = results.first().exceptionOrNull()
        assertTrue(exception is java.io.IOException)
        assertEquals("No internet", exception?.message)
    }
}