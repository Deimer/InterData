package com.testdeymervilla.database.di

import android.content.Context
import androidx.room.Room
import com.testdeymervilla.database.constants.DatabaseConstants.KEY_NAME_DATABASE
import com.testdeymervilla.database.factory.InterDataDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        klass = InterDataDatabase::class.java,
        name = KEY_NAME_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(
        database: InterDataDatabase
    ) = database.getUserDao()

    @Singleton
    @Provides
    fun provideSchemaDao(
        database: InterDataDatabase
    ) = database.getSchemaDao()
}