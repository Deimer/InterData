package com.testdeymervilla.usecase.locality

import com.testdeymervilla.repository.domain.LocalityDomain
import com.testdeymervilla.repository.repositories.locality.ILocalityRepository
import com.testdeymervilla.usecase.data.dummyLocalityDomainList
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
class FetchLocalitiesUseCaseTest {

    @Mock
    private lateinit var mockLocalityRepository: ILocalityRepository

    private lateinit var fetchLocalitiesUseCase: FetchLocalitiesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchLocalitiesUseCase = FetchLocalitiesUseCase(mockLocalityRepository)
    }

    @Test
    fun `invoke should emit success result when repository returns localities`() = runTest {
        val expectedLocalities = dummyLocalityDomainList
        val successFlow = flowOf(Result.success(expectedLocalities))
        `when`(mockLocalityRepository.getLocalities()).thenReturn(successFlow)
        val results = mutableListOf<Result<List<LocalityDomain>>>()
        fetchLocalitiesUseCase().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isSuccess)
        assertEquals(expectedLocalities, results.first().getOrNull())
        verify(mockLocalityRepository).getLocalities()
    }

    @Test
    fun `invoke should emit failure when repository fails`() = runTest {
        val exception = Exception("Network error")
        val errorFlow = flowOf(Result.failure<List<LocalityDomain>>(exception))
        `when`(mockLocalityRepository.getLocalities()).thenReturn(errorFlow)
        val results = mutableListOf<Result<List<LocalityDomain>>>()
        fetchLocalitiesUseCase().toList(results)
        assertEquals(1, results.size)
        assertTrue(results.first().isFailure)
        assertEquals(exception, results.first().exceptionOrNull())
        verify(mockLocalityRepository).getLocalities()
    }

    @Test
    fun `invoke should emit success with empty list when no data is available`() = runTest {
        val successFlow = flowOf(Result.success(emptyList<LocalityDomain>()))
        `when`(mockLocalityRepository.getLocalities()).thenReturn(successFlow)
        val results = mutableListOf<Result<List<LocalityDomain>>>()
        fetchLocalitiesUseCase().toList(results)
        assertTrue(results.first().isSuccess)
        assertTrue(results.first().getOrNull()?.isEmpty() == true)
    }
}