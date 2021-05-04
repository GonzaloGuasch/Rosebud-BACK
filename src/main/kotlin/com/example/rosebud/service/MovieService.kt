package com.example.rosebud.service

import com.example.rosebud.model.Movie
import com.example.rosebud.model.Review
import com.example.rosebud.model.User
import com.example.rosebud.model.wrapper.MovieRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.example.rosebud.repository.MovieRepository
import org.springframework.stereotype.Service

@Service
class MovieService(private val movieRepository: MovieRepository,
                   private val userService: UserService) {

    fun getAllMovies(): List<Movie> = this.movieRepository.findAll()

    fun save(movieToSave: Movie): Movie = this.movieRepository.save(movieToSave)

    fun getMovieByTitle(movieTitle: String): List<Movie>? = this.movieRepository.findByTitleIgnoreCaseContaining(movieTitle)

    fun rateMovie(movieRateWrapper: MovieRateWrapper): Movie {
        val movieToRate: Movie = this.movieRepository.findById(movieRateWrapper.movieTitle).get()
        movieToRate.rate(movieRateWrapper.rate)

        return this.movieRepository.save(movieToRate)
    }

    fun leaveReview(reviewWrapper: ReviewWrapper): Movie {
        val movieToReview = this.movieRepository.findById(reviewWrapper.movieTitle).get()
        val newReview = Review(reviewWrapper.username, reviewWrapper.review)
        movieToReview.addReview(newReview)

        return this.movieRepository.save(movieToReview)
    }

    fun getByTitle(movieTitle: String): Movie? = this.movieRepository.findById(movieTitle).get()

    fun addToWachtedList(wachtedListWrapper: WachtedListWrapper): Boolean {
        val movie: Movie = this.movieRepository.findById(wachtedListWrapper.movieTitle).get()
        val user: User = this.userService.getByUsername(wachtedListWrapper.username)
        user.addMovieWachted(movie)
        this.userService.save(user)
        return true
    }

    fun getStatsForUser(username: String): Any = this.movieRepository.getStatsForUser(username)

}