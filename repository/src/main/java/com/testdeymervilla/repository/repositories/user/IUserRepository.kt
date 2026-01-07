package com.testdeymervilla.repository.repositories.user

import com.testdeymervilla.repository.domain.UserDomain
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getVersion(): Flow<Result<String>>

    fun inSession(): Flow<Result<Boolean>>

    fun login(
        username: String,
        password: String
    ): Flow<Result<Boolean>>

    fun getUser(): Flow<Result<UserDomain>>
}