package com.example.rosebud.controller

import com.example.rosebud.service.implementations.ReviewServiceImpl
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/review")
class ReviewController(private var reviewServiceImpl: ReviewServiceImpl){

    @DeleteMapping("/delete/{movieTitle}/{review_id}")
    fun deleteReview(@PathVariable review_id: String, @PathVariable movieTitle: String) = this.reviewServiceImpl.deleteReview(review_id, movieTitle)
}