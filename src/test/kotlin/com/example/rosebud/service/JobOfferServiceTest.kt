package com.example.rosebud.service

import com.example.rosebud.model.Offers.JobOffer
import com.example.rosebud.repository.JobOfferRepository
import com.example.rosebud.service.implementations.JobOfferServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JobOfferServiceTest {

    private val jobOfferRepository = Mockito.mock(JobOfferRepository::class.java)
    private val jobOfferService = JobOfferServiceImpl(jobOfferRepository)
    val jobOffer = JobOffer("Test", "Description", "TITULO", "23", "Quilmes", 30, "HTTP://youtube")

    @BeforeAll
    fun setUp() {
        Mockito.`when`(jobOfferRepository.save(jobOffer)).thenReturn(jobOffer)
    }


    @Test
    fun test001_SeGuardaUnaNuevaJobOffer() {
        val jobOfferSaved = this.jobOfferService.save(jobOffer)

        Assertions.assertEquals(jobOffer.title, jobOfferSaved.title)
    }

}