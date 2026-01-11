package com.testdeymervilla.repository.mappers

import com.testdeymervilla.network.dto.LocalityDTO
import com.testdeymervilla.repository.data.dummyLocalityDTOList
import com.testdeymervilla.repository.data.dummyLocalityEntityList
import com.testdeymervilla.repository.utils.toCopFormat
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LocalityMapperTest {

    @Test
    fun `test LocalityDTO toEntity maps correctly`() {
        val dto = dummyLocalityDTOList.first()
        val entity = dto.toEntity()
        assertEquals(dto.name.orEmpty(), entity.name)
        assertEquals(dto.fullName.orEmpty(), entity.fullName)
        assertEquals(dto.cityCode.orEmpty(), entity.cityAbbreviation)
        assertEquals(dto.postalCode.orEmpty(), entity.zipCode)
        assertEquals(dto.amount ?: 0f, entity.pickupValue)
    }

    @Test
    fun `test LocalityEntity toDomain maps correctly`() {
        val entity = dummyLocalityEntityList.first()
        val domain = entity.toDomain()
        assertEquals(entity.id, domain.id)
        assertEquals(entity.name, domain.name)
        assertEquals(entity.fullName, domain.fullName)
        assertEquals(entity.pickupValue.toCopFormat(), domain.pickupValue)
    }

    @Test
    fun `test LocalityDTO toEntity with null amount returns zero`() {
        val dto = LocalityDTO(name = "Medellín", amount = null)
        val entity = dto.toEntity()
        assertEquals(0f, entity.pickupValue)
    }

    @Test
    fun `test LocalityDTO toEntity maps all DTO fields to Entity`() {
        val dto = LocalityDTO(
            cityCode = "MDE",
            fullName = "Antioquia, Medellín",
            name = "Medellín",
            postalCode = "050001",
            amount = 150.5f
        )
        val entity = dto.toEntity()
        assertEquals("MDE", entity.cityAbbreviation)
        assertEquals("050001", entity.zipCode)
        assertEquals(150.5f, entity.pickupValue)
    }

    @Test
    fun `test LocalityEntity toDomain handles numeric string conversion`() {
        val entity = dummyLocalityEntityList.first().copy(pickupValue = 1200.0f)
        val domain = entity.toDomain()
        val expected = entity.pickupValue.toCopFormat()
        assertEquals(expected, domain.pickupValue)
    }

    @Test
    fun `test LocalityDTO toEntity with zero amount`() {
        val dto = dummyLocalityDTOList.first().copy(amount = 0f)
        val entity = dto.toEntity()
        assertEquals(0f, entity.pickupValue)
    }
}