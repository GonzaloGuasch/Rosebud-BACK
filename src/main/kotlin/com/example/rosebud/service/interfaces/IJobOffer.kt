package com.example.rosebud.service.interfaces

import com.example.rosebud.model.Offers.JobOffer

interface IJobOffer {
    fun getAllJobsOffers(): List<JobOffer>
    fun save(jobOffer: JobOffer) : JobOffer
    fun applyFilters(locationFilter: String, remuneracionFilter: String): List<JobOffer>
}