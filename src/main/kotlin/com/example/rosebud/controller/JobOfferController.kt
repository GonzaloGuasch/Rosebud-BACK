package com.example.rosebud.controller

import com.example.rosebud.model.Offers.JobOffer
import com.example.rosebud.service.JobOfferService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/jobOffer")
class JobOfferController(private val jobOfferService: JobOfferService) {

    @GetMapping("/all")
    fun getAllJobsOffers() = this.jobOfferService.getAllJobsOffers()

    @PostMapping("/create")
    fun createJobOffer(@RequestBody jobOffer: JobOffer) = this.jobOfferService.save(jobOffer)

    @GetMapping("/applyfilter/{locationfilter}/{remuneracionfilter}")
    fun applyFilter(@PathVariable locationfilter: String,@PathVariable remuneracionfilter: String ) = this.jobOfferService.applyFilters(locationfilter, remuneracionfilter)
}