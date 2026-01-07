package com.testdeymervilla.repository.repositories.user

import com.testdeymervilla.datasource.local.user.IUserLocalDataSource
import com.testdeymervilla.datasource.remote.user.IUserRemoteDataSource
import com.testdeymervilla.repository.mappers.toDomain
import com.testdeymervilla.repository.mappers.toEntity
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userLocalDataSource: IUserLocalDataSource,
    private val userRemoteDataSource: IUserRemoteDataSource
): IUserRepository {

    override fun getVersion() = flow {
        try {
            val version = userRemoteDataSource.getVersion()
            emit(value = Result.success(version))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun inSession() = flow {
        try {
            val inSession = userLocalDataSource.fetch() != null
            emit(value = Result.success(inSession))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun login(
        username: String,
        password: String
    ) = flow {
        try {
            val userEntity = userRemoteDataSource.login(
                username = username,
                password = password
            ).toEntity()
            val isLogged = userLocalDataSource.insert(userEntity)
            emit(value = Result.success(isLogged))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }

    override fun getUser() = flow {
        try {
            emit(value = Result.success(userLocalDataSource.fetch().toDomain()))
        } catch (ioException: IOException) {
            emit(value = Result.failure(ioException))
        } catch (exception: Exception) {
            emit(value = Result.failure(exception))
        }
    }
}