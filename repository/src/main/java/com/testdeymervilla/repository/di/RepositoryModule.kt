package com.testdeymervilla.repository.di

import com.testdeymervilla.repository.repositories.locality.ILocalityRepository
import com.testdeymervilla.repository.repositories.locality.LocalityRepository
import com.testdeymervilla.repository.repositories.schema.ISchemaRepository
import com.testdeymervilla.repository.repositories.schema.SchemaRepository
import com.testdeymervilla.repository.repositories.user.IUserRepository
import com.testdeymervilla.repository.repositories.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        implRepository: UserRepository
    ): IUserRepository

    @Binds
    abstract fun bindSchemaRepository(
        implRepository: SchemaRepository
    ): ISchemaRepository

    @Binds
    abstract fun bindLocalityRepository(
        implRepository: LocalityRepository
    ): ILocalityRepository
}