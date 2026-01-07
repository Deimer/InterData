package com.testdeymervilla.datasource.local.user

import com.testdeymervilla.database.entities.UserEntity

interface IUserLocalDataSource {

    suspend fun insert(
        user: UserEntity
    ): Boolean

    suspend fun update(
        user: UserEntity
    ): Boolean

    suspend fun fetch(): UserEntity?

    suspend fun clear(): Boolean
}