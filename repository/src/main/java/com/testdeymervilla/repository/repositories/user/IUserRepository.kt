package com.testdeymervilla.repository.repositories.user

import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getVersion(): Flow<Result<String>>

    fun login(
        username: String,
        password: String
    ): Flow<Result<Boolean>>
}