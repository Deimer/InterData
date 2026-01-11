package com.testdeymervilla.usecase.locality

import com.testdeymervilla.repository.domain.LocalityDomain
import com.testdeymervilla.repository.repositories.locality.ILocalityRepository
import com.testdeymervilla.usecase.data.dummyLocalityDomain
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class FetchLocalityUseCaseTest {

    @Mock
    private lateinit var mockLocalityRepository: ILocalityRepository

    private lateinit var fetchLocalityUseCase: FetchLocalityUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchLocalityUseCase = FetchLocalityUseCase(mockLocalityRepository)
    }

    @Test
    fun `invoke should emit success when repository returns locality by id`() = runTest {
        val localityId = 1
        val expectedLocality = dummyLocalityDomain
        val successFlow = flowOf(Result.success(expectedLocality))
        `when`(mockLocalityRepository.getLocality(localityId))
            .thenReturn(successFlow)
        val results = mutableListOf<Result<LocalityDomain>>()
        fetchLocalityUseCase(localityId).toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedLocality, results.first().getOrNull())
        verify(mockLocalityRepository).getLocality(localityId)
    }

    @Test
    fun `invoke should emit failure when repository fails to find locality`() = runTest {
        val localityId = 999
        val exception = Exception("Locality not found")
        val errorFlow = flowOf(Result.failure<LocalityDomain>(exception))
        `when`(mockLocalityRepository.getLocality(localityId)).thenReturn(errorFlow)
        val results = mutableListOf<Result<LocalityDomain>>()
        fetchLocalityUseCase(localityId).toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockLocalityRepository).getLocality(localityId)
    }

    @Test
    fun `invoke should handle repository exceptions gracefully`() = runTest {
        val localityId = 1
        val exception = RuntimeException("Database connection error")
        `when`(mockLocalityRepository.getLocality(anyInt()))
            .thenReturn(flowOf(Result.failure(exception)))
        val results = mutableListOf<Result<LocalityDomain>>()
        fetchLocalityUseCase(localityId).toList(results)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
    }
}