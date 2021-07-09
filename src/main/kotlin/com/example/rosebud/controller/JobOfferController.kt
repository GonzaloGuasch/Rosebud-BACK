package com.example.rosebud.controller

import com.example.rosebud.model.Offers.JobOffer
import com.example.rosebud.service.implementations.JobOfferServiceImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/jobOffer")
class JobOfferController(private val jobOfferServiceImpl: JobOfferServiceImpl) {

    @GetMapping("/all")
    fun getAllJobsOffers() = this.jobOfferServiceImpl.getAllJobsOffers()

    @PostMapping("/create")
    fun createJobOffer(@RequestBody jobOffer: JobOffer) = this.jobOfferServiceImpl.save(jobOffer)

    @GetMapping("/applyfilter/{locationfilter}/{remuneracionfilter}/{categoryFilter}")
    fun applyFilter(@PathVariable locationfilter: String,
                    @PathVariable remuneracionfilter: String,
                    @PathVariable categoryFilter: String) = this.jobOfferServiceImpl.applyFilters(locationfilter, remuneracionfilter, categoryFilter)
}