package com.testdeymervilla.interdata.di

import com.testdeymervilla.repository.repositories.schema.ISchemaRepository
import com.testdeymervilla.repository.repositories.user.IUserRepository
import com.testdeymervilla.usecase.schema.FetchSchemaUseCase
import com.testdeymervilla.usecase.schema.FetchSchemasUseCase
import com.testdeymervilla.usecase.user.FetchVersionUseCase
import com.testdeymervilla.usecase.user.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(
        userRepository: IUserRepository
    ) = LoginUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun provideFetchVersionUseCase(
        userRepository: IUserRepository
    ) = FetchVersionUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun provideFetchSchemasUseCase(
        schemaRepository: ISchemaRepository
    ) = FetchSchemasUseCase(schemaRepository)

    @Provides
    @ViewModelScoped
    fun provideFetchSchemaUseCase(
        schemaRepository: ISchemaRepository
    ) = FetchSchemaUseCase(schemaRepository)
}