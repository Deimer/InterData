package com.testdeymervilla.database.factory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.testdeymervilla.database.constants.DatabaseConstants.DATABASE_VERSION
import com.testdeymervilla.database.dao.SchemaDao
import com.testdeymervilla.database.dao.UserDao
import com.testdeymervilla.database.entities.SchemaEntity
import com.testdeymervilla.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        SchemaEntity::class
    ],
    version = DATABASE_VERSION
)
abstract class InterDataDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getSchemaDao(): SchemaDao
}