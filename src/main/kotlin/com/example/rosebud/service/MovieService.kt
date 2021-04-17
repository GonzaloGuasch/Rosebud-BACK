package com.example.rosebud.service

import com.example.rosebud.model.Movie
import com.example.rosebud.repository.MovieRepository
import org.springframework.stereotype.Service

@Service
class MovieService(private var movieRepository: MovieRepository) {

    fun getAllMovies(): List<Movie> = this.movieRepository.findAll()

    fun save(movieToSave: Movie): Movie = this.movieRepository.save(movieToSave)

    fun getMovieByTitle(movieTitle: String): List<Movie>? = this.movieRepository.findByTitleIgnoreCaseContaining(movieTitle)

}