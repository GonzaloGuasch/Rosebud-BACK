package com.example.rosebud.repository

import com.example.rosebud.model.Offers.JobOffer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobOfferRepository : JpaRepository<JobOffer, String>{
}