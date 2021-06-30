package com.example.rosebud.service

import com.example.rosebud.model.Disk
import com.example.rosebud.model.Duration
import com.example.rosebud.repository.DiskRepository
import com.example.rosebud.repository.DurationRepository
import com.example.rosebud.service.implementations.DiskServiceImpl
import com.example.rosebud.service.implementations.DurationServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DiskServiceTest {

    private val diskRepository = Mockito.mock(DiskRepository::class.java)
    private val diskService = DiskServiceImpl(diskRepository)
    private val duration = Duration(2, 30)
    private val disk = Disk("TEST_BAND", "TEST", 2021, "A DISK TEST", "ROCK", duration)

    @BeforeAll
    fun setUp() {
        Mockito.`when`(diskRepository.findAll()).thenReturn(listOf(disk))
        Mockito.`when`(diskRepository.save(disk)).thenReturn(disk)
    }

    @Test
    fun test001_() {
        this.diskRepository.save(disk)

        Assertions.assertEquals(1, this.diskService.getAllDisks().size)
    }
}