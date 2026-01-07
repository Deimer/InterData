package com.testdeymervilla.usecase.user

import com.testdeymervilla.repository.repositories.user.IUserRepository
import javax.inject.Inject

class InSessionUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {

    operator fun invoke() =
        userRepository.inSession()
}