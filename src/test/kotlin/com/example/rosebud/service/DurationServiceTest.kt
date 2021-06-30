package com.example.rosebud.service

import com.example.rosebud.model.Duration
import com.example.rosebud.repository.DurationRepository
import com.example.rosebud.service.implementations.DurationServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DurationServiceTest {

    private val durationRepository = Mockito.mock(DurationRepository::class.java)
    private val durationService = DurationServiceImpl(durationRepository)
    private val duration = Duration(2, 30)

    @BeforeAll
    fun setUp() {
        Mockito.`when`(durationRepository.save(duration)).thenReturn(duration)
    }

    @Test
    fun test001_() {
        val durationSaved = this.durationService.save(duration)

        Assertions.assertEquals(duration.amountOfMinutes(), durationSaved.amountOfMinutes())
    }
}