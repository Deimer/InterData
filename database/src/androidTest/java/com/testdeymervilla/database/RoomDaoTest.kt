package com.testdeymervilla.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.testdeymervilla.database.dao.LocalityDao
import com.testdeymervilla.database.dao.SchemaDao
import com.testdeymervilla.database.dao.UserDao
import com.testdeymervilla.database.data.dummyLocalityEntity
import com.testdeymervilla.database.data.dummyLocalityEntityList
import com.testdeymervilla.database.data.dummySchemaEntityList
import com.testdeymervilla.database.data.dummyUserEntity
import com.testdeymervilla.database.data.dummyUserEntityUpdated
import com.testdeymervilla.database.factory.InterDataDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RoomDaoTest {

    private lateinit var database: InterDataDatabase
    private lateinit var localityDao: LocalityDao
    private lateinit var schemaDao: SchemaDao
    private lateinit var userDao: UserDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, InterDataDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        localityDao = database.getLocalityDao()
        schemaDao = database.getSchemaDao()
        userDao = database.getUserDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndFetchLocalities() = runBlocking {
        val localities = dummyLocalityEntityList
        localityDao.insert(localities)
        val result = localityDao.fetch()
        assertEquals(2, result.size)
        assertEquals("Name 2", result[0].name)
    }

    @Test
    fun fetchLocalityById() = runBlocking {
        val locality = dummyLocalityEntity
        localityDao.insert(listOf(locality))
        val result = localityDao.fetchById(10)
        assertNotNull(result)
        assertEquals("Target", result.fullName)
    }

    @Test
    fun clearLocalities() = runBlocking {
        localityDao.insert(dummyLocalityEntityList)
        localityDao.clear()
        val result = localityDao.fetch()
        assertTrue(result.isEmpty())
    }

    @Test
    fun insertAndFetchSchemas() = runBlocking {
        schemaDao.insert(dummySchemaEntityList)
        val result = schemaDao.fetch()
        assertEquals(1, result.size)
        assertEquals("Table1", result[0].tableName)
    }

    @Test
    fun insertAndFetchUser() = runBlocking {
        userDao.insert(dummyUserEntity)
        val result = userDao.fetch()
        assertNotNull(result)
        assertEquals("admin", result?.user)
    }

    @Test
    fun updateUser() = runBlocking {
        userDao.insert(dummyUserEntityUpdated)
        val updatedUser = dummyUserEntityUpdated.copy(fullName = "Updated")
        userDao.update(updatedUser)
        val result = userDao.fetch()
        assertEquals("Updated", result?.fullName)
    }
}