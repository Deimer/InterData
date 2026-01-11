package com.testdeymervilla.repository.mappers

import com.testdeymervilla.network.dto.UserDTO
import com.testdeymervilla.repository.data.dummyExpectedUserDto
import com.testdeymervilla.repository.data.dummyUserEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class UserMapperTest {

    @Test
    fun `test UserDTO toEntity maps correctly`() {
        val userDTO = dummyExpectedUserDto
        val userEntity = userDTO.toEntity()
        assertEquals(userDTO.username.orEmpty(), userEntity.user)
        assertEquals(userDTO.identification.orEmpty(), userEntity.identification)
        assertEquals(userDTO.fullName.orEmpty(), userEntity.fullName)
    }

    @Test
    fun `test UserEntity toDomain maps correctly`() {
        val userEntity = dummyUserEntity
        val userDomain = userEntity.toDomain()
        assertEquals(userEntity.id, userDomain.id)
        assertEquals(userEntity.user, userDomain.username)
        assertEquals(userEntity.identification, userDomain.identification)
        assertEquals(userEntity.fullName, userDomain.fullName)
        assertTrue(userDomain.shortName.isNotEmpty())
    }

    @Test
    fun `test UserDTO toEntity with null fields returns empty strings`() {
        val dto = UserDTO(username = null, identification = null, fullName = null)
        val entity = dto.toEntity()
        assertEquals("", entity.user)
        assertEquals("987204545", entity.identification)
        assertEquals("Deymer Villa Pedraza", entity.fullName)
    }

    @Test
    fun `test UserEntity toDomain calculates shortName correctly`() {
        val entity = dummyUserEntity.copy(fullName = "Juan Carlos Perez")
        val domain = entity.toDomain()
        assertEquals("Juan", domain.shortName)
    }

    @Test
    fun `test UserEntity toDomain with single name handles shortName correctly`() {
        val entity = dummyUserEntity.copy(fullName = "Admin")
        val domain = entity.toDomain()
        assertEquals("Admin", domain.shortName)
    }
}