package com.example.rosebud.service.implementations

import com.example.rosebud.model.Offers.JobOffer
import com.example.rosebud.repository.JobOfferRepository
import com.example.rosebud.service.interfaces.IJobOffer
import org.springframework.stereotype.Service

@Service
class JobOfferServiceImpl(private val jobOfferRepository : JobOfferRepository): IJobOffer {

    override fun getAllJobsOffers() = this.jobOfferRepository.findAll()
    override fun save(jobOffer: JobOffer) = this.jobOfferRepository.save(jobOffer)
    override fun applyFilters(locationFilter: String, remuneracionFilter: String) = this.jobOfferRepository.findByLocationIgnoreCaseContainingAndRemunerationIgnoreCaseContaining(locationFilter, remuneracionFilter)

}