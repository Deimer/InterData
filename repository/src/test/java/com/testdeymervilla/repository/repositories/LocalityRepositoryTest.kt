package com.testdeymervilla.repository.repositories

import com.testdeymervilla.datasource.local.locality.ILocalityLocalDataSource
import com.testdeymervilla.datasource.remote.locality.ILocalityRemoteDataSource
import com.testdeymervilla.repository.data.dummyLocalityDTOList
import com.testdeymervilla.repository.data.dummyLocalityEntityList
import com.testdeymervilla.repository.domain.LocalityDomain
import com.testdeymervilla.repository.repositories.locality.LocalityRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

@ExperimentalCoroutinesApi
class LocalityRepositoryTest {

    @Mock
    private lateinit var mockLocalityLocalDataSource: ILocalityLocalDataSource

    @Mock
    private lateinit var mockLocalityRemoteDataSource: ILocalityRemoteDataSource

    private lateinit var localityRepository: LocalityRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        localityRepository = LocalityRepository(mockLocalityLocalDataSource, mockLocalityRemoteDataSource)
    }

    @Test
    fun `getLocalities emits local data when database is not empty`() = runTest {
        val localLocalities = dummyLocalityEntityList
        `when`(mockLocalityLocalDataSource.fetch()).thenReturn(localLocalities)
        val results = mutableListOf<Result<List<LocalityDomain>>>()
        localityRepository.getLocalities().toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(localLocalities.size, results.first().getOrNull()?.size)
        verifyNoInteractions(mockLocalityRemoteDataSource)
    }

    @Test
    fun `getLocalities fetches from remote and saves when database is empty`() = runTest {
        val remoteLocalities = dummyLocalityDTOList
        val entitiesAfterInsert = dummyLocalityEntityList
        `when`(mockLocalityLocalDataSource.fetch())
            .thenReturn(emptyList())
            .thenReturn(entitiesAfterInsert)
        `when`(mockLocalityRemoteDataSource.fetch()).thenReturn(remoteLocalities)
        `when`(mockLocalityLocalDataSource.insert(any())).thenReturn(true)
        val results = mutableListOf<Result<List<LocalityDomain>>>()
        localityRepository.getLocalities().toList(results)
        assertTrue(results.first().isSuccess)
        verify(mockLocalityRemoteDataSource).fetch()
        verify(mockLocalityLocalDataSource).insert(any())
    }

    @Test
    fun `getLocality emits specific locality domain`() = runTest {
        val localityId = 1
        val entity = dummyLocalityEntityList[0]
        `when`(mockLocalityLocalDataSource.fetchById(localityId)).thenReturn(entity)
        val results = mutableListOf<Result<LocalityDomain>>()
        localityRepository.getLocality(localityId).toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(entity.name, results.first().getOrNull()?.name)
        verify(mockLocalityLocalDataSource).fetchById(localityId)
    }

    @Test
    fun `getLocality emits failure when data source throws exception`() = runTest {
        val exception = RuntimeException("Local error")
        `when`(mockLocalityLocalDataSource.fetchById(anyInt())).thenThrow(exception)
        val results = mutableListOf<Result<LocalityDomain>>()
        localityRepository.getLocality(1).toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
    }

    @Test
    fun `clearLocality emits result from local data source`() = runTest {
        val localityId = 1
        `when`(mockLocalityLocalDataSource.clearById(localityId)).thenReturn(true)
        val results = mutableListOf<Result<Boolean>>()
        localityRepository.clearLocality(localityId).toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(true, results.first().getOrNull())
    }

    @Test
    fun `clearLocalities emits true when successfully cleared`() = runTest {
        `when`(mockLocalityLocalDataSource.clear()).thenReturn(true)
        val results = mutableListOf<Result<Boolean>>()
        localityRepository.clearLocalities().toList(results)
        assertTrue(results.first().isSuccess)
        assertEquals(true, results.first().getOrNull())
        verify(mockLocalityLocalDataSource).clear()
    }
}