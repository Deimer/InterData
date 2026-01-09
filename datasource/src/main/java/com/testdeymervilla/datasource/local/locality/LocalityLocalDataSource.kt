package com.testdeymervilla.datasource.local.locality

import com.testdeymervilla.database.dao.LocalityDao
import com.testdeymervilla.database.entities.LocalityEntity
import javax.inject.Inject

class LocalityLocalDataSource @Inject constructor(
    private val localityDao: LocalityDao
): ILocalityLocalDataSource {

    override suspend fun insert(localities: List<LocalityEntity>): Boolean {
        val result = localityDao.insert(localities)
        return result.isNotEmpty() && result.all { it > 0 }
    }

    override suspend fun fetch() =
        localityDao.fetch()

    override suspend fun fetchById(localityId: Int) =
        localityDao.fetchById(localityId)

    override suspend fun clear() =
        localityDao.clear() > 0

    override suspend fun clearById(localityId: Int) =
        localityDao.clearById(localityId) == 1
}