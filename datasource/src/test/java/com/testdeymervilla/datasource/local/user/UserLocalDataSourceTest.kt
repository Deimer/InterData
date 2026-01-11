package com.testdeymervilla.datasource.local.user

import com.testdeymervilla.database.dao.UserDao
import com.testdeymervilla.datasource.data.dummyUserEntity
import com.testdeymervilla.datasource.data.dummyUserEntityUpdated
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
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
class UserLocalDataSourceTest {

    @Mock
    private lateinit var mockUserDao: UserDao

    private lateinit var userLocalDataSource: UserLocalDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userLocalDataSource = UserLocalDataSource(mockUserDao)
    }

    @Test
    fun `insert should return true when insertion is successful`() = runTest {
        val user = dummyUserEntity
        `when`(mockUserDao.insert(user)).thenReturn(1L)
        val result = userLocalDataSource.insert(user)
        assertTrue(result)
        verify(mockUserDao).insert(user)
    }

    @Test
    fun `insert should return false when insertion fails`() = runTest {
        val user = dummyUserEntity
        `when`(mockUserDao.insert(user)).thenReturn(-1L)
        val result = userLocalDataSource.insert(user)
        assertFalse(result)
    }

    @Test
    fun `update should return true when update is successful`() = runTest {
        val user = dummyUserEntityUpdated
        `when`(mockUserDao.update(user)).thenReturn(1)
        val result = userLocalDataSource.update(user)
        assertTrue(result)
        verify(mockUserDao).update(user)
    }

    @Test
    fun `update should return false when no rows are updated`() = runTest {
        val user = dummyUserEntityUpdated
        `when`(mockUserDao.update(user)).thenReturn(0)
        val result = userLocalDataSource.update(user)
        assertFalse(result)
    }

    @Test
    fun `fetch should return user entity when it exists`() = runTest {
        val expectedUser = dummyUserEntity
        `when`(mockUserDao.fetch()).thenReturn(expectedUser)
        val result = userLocalDataSource.fetch()
        assertEquals(expectedUser, result)
        verify(mockUserDao).fetch()
    }

    @Test
    fun `fetch should return null when no user exists`() = runTest {
        `when`(mockUserDao.fetch()).thenReturn(null)
        val result = userLocalDataSource.fetch()
        assertNull(result)
    }

    @Test
    fun `clear should return true when deletion is successful`() = runTest {
        `when`(mockUserDao.clear()).thenReturn(1)
        val result = userLocalDataSource.clear()
        assertTrue(result)
        verify(mockUserDao).clear()
    }

    @Test
    fun `clear should return false when table is already empty`() = runTest {
        `when`(mockUserDao.clear()).thenReturn(0)
        val result = userLocalDataSource.clear()
        assertFalse(result)
    }
}