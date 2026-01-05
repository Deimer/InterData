package com.testdeymervilla.datasource.local.user

import com.testdeymervilla.database.dao.UserDao
import com.testdeymervilla.database.entities.UserEntity
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao
): IUserLocalDataSource {

    override suspend fun insert(user: UserEntity) =
        userDao.insert(user) > 0

    override suspend fun update(user: UserEntity) =
        userDao.update(user) > 0

    override suspend fun fetch() =
        userDao.fetch()

    override suspend fun clear() =
        userDao.clear() > 0
}