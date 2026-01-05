package com.testdeymervilla.usecase.user

import com.testdeymervilla.repository.repositories.user.IUserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: IUserRepository
) {

    operator fun invoke(
        username: String,
        password: String
    ) = userRepository.login(
        username = username,
        password = password
    )
}