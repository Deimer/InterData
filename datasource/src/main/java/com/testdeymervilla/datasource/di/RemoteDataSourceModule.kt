package com.testdeymervilla.datasource.di

import com.testdeymervilla.datasource.remote.schema.ISchemaRemoteDataSource
import com.testdeymervilla.datasource.remote.schema.SchemaRemoteDataSource
import com.testdeymervilla.datasource.remote.user.IUserRemoteDataSource
import com.testdeymervilla.datasource.remote.user.UserRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindUserRemoteDataSource(
        implUserRemoteDataSource: UserRemoteDataSource
    ): IUserRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindSchemaRemoteDataSource(
        implSchemaRemoteDataSource: SchemaRemoteDataSource
    ): ISchemaRemoteDataSource
}