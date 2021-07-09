package com.example.rosebud.repository

import com.example.rosebud.model.Offers.JobOffer
import org.springframework.data.jpa.repository.JpaRepository

import org.springframework.stereotype.Repository

@Repository
interface JobOfferRepository : JpaRepository<JobOffer, Long> {
    fun findByLocationIgnoreCaseContainingAndRemunerationIgnoreCaseContainingAndCategoryIgnoreCaseContaining(locationFilter: String, remuneracionFilter: String, categoryFilter: String): List<JobOffer>
}