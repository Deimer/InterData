package com.testdeymervilla.usecase.locality

import com.testdeymervilla.repository.repositories.locality.ILocalityRepository
import javax.inject.Inject

class FetchLocalityUseCase @Inject constructor(
    private val localityRepository: ILocalityRepository
) {

    operator fun invoke(
        localityId: Int
    ) = localityRepository.getLocality(
        localityId
    )
}