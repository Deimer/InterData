package com.testdeymervilla.datasource.local.locality

import com.testdeymervilla.database.dao.LocalityDao
import com.testdeymervilla.datasource.data.dummyLocalityEntityList
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
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
class LocalityLocalDataSourceTest {

    @Mock
    private lateinit var mockLocalityDao: LocalityDao

    private lateinit var localityLocalDataSource: LocalityLocalDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        localityLocalDataSource = LocalityLocalDataSource(mockLocalityDao)
    }

    @Test
    fun `insert should return true when all localities are inserted successfully`() = runTest {
        val localities = dummyLocalityEntityList
        `when`(mockLocalityDao.insert(localities)).thenReturn(listOf(1L, 2L))
        val result = localityLocalDataSource.insert(localities)
        assertTrue(result)
        verify(mockLocalityDao).insert(localities)
    }

    @Test
    fun `insert should return false when insertion result is empty`() = runTest {
        val localities = dummyLocalityEntityList
        `when`(mockLocalityDao.insert(localities)).thenReturn(emptyList())
        val result = localityLocalDataSource.insert(localities)
        assertFalse(result)
    }

    @Test
    fun `fetch should return all localities from dao`() = runTest {
        val expectedLocalities = dummyLocalityEntityList
        `when`(mockLocalityDao.fetch()).thenReturn(expectedLocalities)
        val result = localityLocalDataSource.fetch()
        assertEquals(expectedLocalities.size, result.size)
        assertEquals(expectedLocalities, result)
        verify(mockLocalityDao).fetch()
    }

    @Test
    fun `fetchById should return specific locality`() = runTest {
        val localityId = 1
        val expectedLocality = dummyLocalityEntityList[0]
        `when`(mockLocalityDao.fetchById(localityId)).thenReturn(expectedLocality)
        val result = localityLocalDataSource.fetchById(localityId)
        assertEquals(expectedLocality, result)
        assertEquals(expectedLocality.name, result.name)
        verify(mockLocalityDao).fetchById(localityId)
    }

    @Test
    fun `clear should return true when rows are deleted`() = runTest {
        `when`(mockLocalityDao.clear()).thenReturn(10)
        val result = localityLocalDataSource.clear()
        assertTrue(result)
        verify(mockLocalityDao).clear()
    }

    @Test
    fun `clearById should return true when the row is successfully deleted`() = runTest {
        val localityId = 1
        `when`(mockLocalityDao.clearById(localityId)).thenReturn(1)
        val result = localityLocalDataSource.clearById(localityId)
        assertTrue(result)
        verify(mockLocalityDao).clearById(localityId)
    }

    @Test
    fun `clearById should return false when row does not exist`() = runTest {
        val localityId = 99
        `when`(mockLocalityDao.clearById(localityId)).thenReturn(0)
        val result = localityLocalDataSource.clearById(localityId)
        assertFalse(result)
    }
}