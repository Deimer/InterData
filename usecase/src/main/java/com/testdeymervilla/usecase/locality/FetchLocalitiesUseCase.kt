package com.testdeymervilla.usecase.locality

import com.testdeymervilla.repository.repositories.locality.ILocalityRepository
import javax.inject.Inject

class FetchLocalitiesUseCase @Inject constructor(
    private val localityRepository: ILocalityRepository
) {

    operator fun invoke() =
        localityRepository.getLocalities()
}