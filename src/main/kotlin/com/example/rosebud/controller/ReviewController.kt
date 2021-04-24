package com.example.rosebud.controller

import com.example.rosebud.service.ReviewService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/review")
class ReviewController(private var reviewService: ReviewService){

    @DeleteMapping("/delete/{movieTitle}/{review_id}")
    fun deleteReview(@PathVariable review_id: String, @PathVariable movieTitle: String) = this.reviewService.deleteReview(review_id, movieTitle)
}