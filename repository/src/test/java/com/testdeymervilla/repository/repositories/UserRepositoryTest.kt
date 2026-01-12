package com.testdeymervilla.repository.repositories

import com.testdeymervilla.datasource.local.user.IUserLocalDataSource
import com.testdeymervilla.datasource.remote.user.IUserRemoteDataSource
import com.testdeymervilla.repository.data.dummyException
import com.testdeymervilla.repository.data.dummyExpectedUserDto
import com.testdeymervilla.repository.data.dummyUserEntity
import com.testdeymervilla.repository.domain.UserDomain
import com.testdeymervilla.repository.repositories.user.UserRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @Mock
    private lateinit var mockUserLocalDataSource: IUserLocalDataSource

    @Mock
    private lateinit var mockUserRemoteDataSource: IUserRemoteDataSource

    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepository(mockUserLocalDataSource, mockUserRemoteDataSource)
    }

    @Test
    fun `getVersion emits success when remote data is available`() = runTest {
        val expectedVersion = "1.0.5"
        `when`(mockUserRemoteDataSource.getVersion()).thenReturn(expectedVersion)
        val results = mutableListOf<Result<String>>()
        userRepository.getVersion().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedVersion, results.first().getOrNull())
        verify(mockUserRemoteDataSource).getVersion()
    }

    @Test
    fun `inSession emits true when user exists in local storage`() = runTest {
        `when`(mockUserLocalDataSource.fetch()).thenReturn(dummyUserEntity)
        val results = mutableListOf<Result<Boolean>>()
        userRepository.inSession().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().getOrNull() == true)
        verify(mockUserLocalDataSource).fetch()
    }

    @Test
    fun `inSession emits false when no user is found locally`() = runTest {
        `when`(mockUserLocalDataSource.fetch()).thenReturn(null)
        val results = mutableListOf<Result<Boolean>>()
        userRepository.inSession().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().getOrNull() == false)
    }

    @Test
    fun `login emits success and returns true when credentials are correct`() = runTest {
        val userDto = dummyExpectedUserDto
        `when`(mockUserRemoteDataSource.login(anyString(), anyString())).thenReturn(userDto)
        `when`(mockUserLocalDataSource.insert(any())).thenReturn(true)
        val results = mutableListOf<Result<Boolean>>()
        userRepository.login("admin", "123").toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull() == true)
        verify(mockUserRemoteDataSource).login("admin", "123")
    }

    @Test
    fun `login emits failure when remote login fails`() = runTest {
        val exception = dummyException
        `when`(mockUserRemoteDataSource.login(anyString(), anyString())).thenThrow(exception)
        val results = mutableListOf<Result<Boolean>>()
        userRepository.login("wrong", "wrong").toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isFailure)
        assertEquals(exception.message, results.first().exceptionOrNull()?.message)
    }

    @Test
    fun `getUser emits user domain data when fetch is successful`() = runTest {
        val entity = dummyUserEntity
        `when`(mockUserLocalDataSource.fetch()).thenReturn(entity)
        val results = mutableListOf<Result<UserDomain>>()
        userRepository.getUser().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(entity.user, results.first().getOrNull()?.username)
        verify(mockUserLocalDataSource).fetch()
    }

    @Test
    fun `getUser emits failure when fetch throws exception`() = runTest {
        val exception = RuntimeException("Database error")
        `when`(mockUserLocalDataSource.fetch()).thenThrow(exception)
        val results = mutableListOf<Result<UserDomain>>()
        userRepository.getUser().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
    }
}