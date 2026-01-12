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
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    @Mock
    private lateinit var mockUserRepository: IUserRepository

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loginUseCase = LoginUseCase(mockUserRepository)
    }

    @Test
    fun `invoke should emit success true when credentials are valid`() = runTest {
        val username = "admin"
        val password = "123"
        `when`(mockUserRepository.login(username, password))
            .thenReturn(flowOf(Result.success(true)))
        val results = mutableListOf<Result<Boolean>>()
        loginUseCase(username, password).toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(true, results.first().getOrNull())
        verify(mockUserRepository).login(username, password)
    }

    @Test
    fun `invoke should emit success false when credentials are invalid`() = runTest {
        val username = "wrong"
        val password = "pwd"
        `when`(mockUserRepository.login(username, password)).thenReturn(flowOf(Result.success(false)))
        val results = mutableListOf<Result<Boolean>>()
        loginUseCase(username, password).toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(false, results.first().getOrNull())
        verify(mockUserRepository).login(username, password)
    }

    @Test
    fun `invoke should emit failure when repository throws exception`() = runTest {
        val exception = RuntimeException("Network Error")
        `when`(mockUserRepository.login(anyString(), anyString()))
            .thenReturn(flowOf(Result.failure(exception)))
        val results = mutableListOf<Result<Boolean>>()
        loginUseCase("user", "pass").toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
    }

    @Test
    fun `invoke should handle empty username or password`() = runTest {
        `when`(mockUserRepository.login("", ""))
            .thenReturn(flowOf(Result.success(false)))
        val results = mutableListOf<Result<Boolean>>()
        loginUseCase("", "").toList(results)
        assertEquals(false, results.first().getOrNull())
        verify(mockUserRepository).login("", "")
    }
}