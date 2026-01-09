package com.testdeymervilla.repository.repositories.locality

import com.testdeymervilla.repository.domain.LocalityDomain
import kotlinx.coroutines.flow.Flow

interface ILocalityRepository {

    fun getLocalities(): Flow<Result<List<LocalityDomain>>>

    fun getLocality(localityId: Int): Flow<Result<LocalityDomain>>

    fun clearLocality(localityId: Int): Flow<Result<Boolean>>

    fun clearLocalities(): Flow<Result<Boolean>>
}