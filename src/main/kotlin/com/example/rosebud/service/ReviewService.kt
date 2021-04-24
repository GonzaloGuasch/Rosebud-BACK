package com.example.rosebud.service

import com.example.rosebud.model.Movie
import com.example.rosebud.model.Review
import com.example.rosebud.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(private val reviewRepository: ReviewRepository,
                    private val movieService: MovieService) {

    fun deleteReview(reviewId: String, movieTitle: String) {
        val review: Review = this.reviewRepository.findById(reviewId.toInt()).get()
        val movie: Movie = this.movieService.getMovieByTitle(movieTitle)!!.first()
        movie.reviews.remove(review)
        this.movieService.save(movie)
        this.reviewRepository.delete(review)
    }
}