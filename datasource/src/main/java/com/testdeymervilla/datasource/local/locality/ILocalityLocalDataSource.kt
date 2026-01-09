package com.testdeymervilla.datasource.local.locality

import com.testdeymervilla.database.entities.LocalityEntity

interface ILocalityLocalDataSource {

    suspend fun insert(
        localities: List<LocalityEntity>
    ): Boolean

    suspend fun fetch(): List<LocalityEntity>

    suspend fun fetchById(
        localityId: Int
    ): LocalityEntity

    suspend fun clear(): Boolean

    suspend fun clearById(
        localityId: Int
    ): Boolean
}