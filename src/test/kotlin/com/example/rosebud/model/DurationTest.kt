package com.example.rosebud.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class DurationTest {
    private lateinit var durationTest: Duration

    @BeforeEach
    fun setUp() {
        durationTest = Duration(2, 40)
    }

    @Test
    fun test001_() {
        Assertions.assertEquals(160, durationTest.amountOfMinutes())
    }
}