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
    private val NO_LOCATION = "No location"
    private val NO_REMUNERATION = "No remunerada"
    private val LOCATION = "QUILMES"
    val jobOffer = JobOffer("Test", "Description", "TITULO", "No remunerada", LOCATION, 30, "HTTP://youtube")

    @BeforeAll
    fun setUp() {
        Mockito.`when`(jobOfferRepository.save(jobOffer)).thenReturn(jobOffer)
        Mockito.`when`(jobOfferRepository.findAll()).thenReturn(listOf(jobOffer))
        Mockito.`when`(jobOfferRepository.findByLocationIgnoreCaseContainingAndRemunerationIgnoreCaseContainingAndCategoryIgnoreCaseContaining(NO_LOCATION, NO_REMUNERATION, "")).thenReturn(listOf())
        Mockito.`when`(jobOfferRepository.findByLocationIgnoreCaseContainingAndRemunerationIgnoreCaseContainingAndCategoryIgnoreCaseContaining(LOCATION, NO_REMUNERATION, "")).thenReturn(listOf(jobOffer))
    }


    @Test
    fun test001_SeGuardaUnaNuevaJobOffer() {
        val jobOfferSaved = this.jobOfferService.save(jobOffer)

        Assertions.assertEquals(jobOffer.title, jobOfferSaved.title)
    }

    @Test
    fun test002_MeTraigoTodasLasJobOfferDesdeElBack() {
        this.jobOfferService.save(jobOffer)
        val listOfJobOfferSaved = this.jobOfferService.getAllJobsOffers()
        Assertions.assertEquals(1, listOfJobOfferSaved.size)
    }

    @Test
    fun test003_CuandoLosFiltrosAplicadosNoAplicanANingunaOfertaEnLaBaseSeDevuelveUnaListaVacia() {
        val emptyJobList = this.jobOfferService.applyFilters(NO_LOCATION, NO_REMUNERATION, "")

        Assertions.assertTrue(emptyJobList.isEmpty())
    }

    @Test
    fun test004_SiLosFiltrosAplicanRetornanLaLista() {
        val jobListWithFIlter = this.jobOfferService.applyFilters(LOCATION, NO_REMUNERATION, "")

        Assertions.assertFalse(jobListWithFIlter.isEmpty())

    }

}