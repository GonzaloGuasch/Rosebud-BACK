package com.example.rosebud.service

import com.example.rosebud.model.Offers.JobOffer
import com.example.rosebud.repository.JobOfferRepository
import org.springframework.stereotype.Service

@Service
class JobOfferService(private val jobOfferRepository : JobOfferRepository) {

    fun getAllJobsOffers() = this.jobOfferRepository.findAll()

    fun save(jobOffer: JobOffer) = this.jobOfferRepository.save(jobOffer)
    fun applyFilters(locationFilter: String, remuneracionFilter: String) = this.jobOfferRepository.findByLocationIgnoreCaseContainingAndRemunerationIgnoreCaseContaining(locationFilter, remuneracionFilter)

}