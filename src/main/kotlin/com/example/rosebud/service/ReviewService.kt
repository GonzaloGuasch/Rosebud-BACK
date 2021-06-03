package com.example.rosebud.service

import com.example.rosebud.model.Element
import com.example.rosebud.model.Movie
import com.example.rosebud.model.Review
import com.example.rosebud.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(private val reviewRepository: ReviewRepository,
                    private val elementService: ElementService) {

    fun deleteReview(reviewId: String, movieTitle: String) {
        val review: Review = this.reviewRepository.findById(reviewId.toInt()).get()
        val movie: Element = this.elementService.getElementByTitle(movieTitle)!!
        movie.reviews.remove(review)
        this.reviewRepository.delete(review)
        this.elementService.saveMovieWithoutPicture(movie as Movie)
    }
}