package com.example.rosebud.service

import com.example.rosebud.model.Movie
import com.example.rosebud.model.wrapper.MovieRateWrapper
import com.example.rosebud.repository.MovieRepository
import org.springframework.stereotype.Service

@Service
class MovieService(private var movieRepository: MovieRepository) {

    fun getAllMovies(): List<Movie> = this.movieRepository.findAll()

    fun save(movieToSave: Movie): Movie = this.movieRepository.save(movieToSave)

    fun getMovieByTitle(movieTitle: String): List<Movie>? = this.movieRepository.findByTitleIgnoreCaseContaining(movieTitle)

    fun rateMovie(movieRateWrapper: MovieRateWrapper): Movie {
        val movieToRate: Movie = this.movieRepository.findById(movieRateWrapper.movieTitle).get()
        movieToRate.rate(movieRateWrapper.rate)
        this.movieRepository.save(movieToRate)

        return movieToRate
    }

}