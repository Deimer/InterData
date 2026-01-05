package com.testdeymervilla.datasource.di

import com.testdeymervilla.datasource.local.schema.ISchemaLocalDataSource
import com.testdeymervilla.datasource.local.schema.SchemaLocalDataSource
import com.testdeymervilla.datasource.local.user.IUserLocalDataSource
import com.testdeymervilla.datasource.local.user.UserLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Singleton
    @Binds
    abstract fun bindUserLocalDataSource(
        implUserLocalDataSource: UserLocalDataSource
    ): IUserLocalDataSource

    @Singleton
    @Binds
    abstract fun bindSchemaLocalDataSource(
        implSchemaLocalDataSource: SchemaLocalDataSource
    ): ISchemaLocalDataSource
}