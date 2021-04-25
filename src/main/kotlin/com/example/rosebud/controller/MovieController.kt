package com.example.rosebud.controller

import com.example.rosebud.model.Movie
import com.example.rosebud.model.wrapper.MovieRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.service.MovieService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/movie")
class MovieController(private var movieService: MovieService) {

    @GetMapping("/")
    fun getAllMovies(): List<Movie>? = this.movieService.getAllMovies()

    @GetMapping("searchByTitle/{movieTitle}")
    fun getMovieByTitle(@PathVariable movieTitle: String): List<Movie>? = this.movieService.getMovieByTitle(movieTitle)

    @GetMapping("getByTitle/{movieTitle}")
    fun getByTitle(@PathVariable movieTitle: String): Movie? = this.movieService.getByTitle(movieTitle)

    @PostMapping("/create")
    fun createMovie(@RequestBody movieToSave: Movie): Movie = this.movieService.save(movieToSave)

    @PostMapping("/rate")
    fun rateMovie(movieRateWrapper: MovieRateWrapper): Movie = this.movieService.rateMovie(movieRateWrapper)

    @PostMapping("/leaveReview")
    fun leaveReview(reviewWrapper: ReviewWrapper): Movie = this.movieService.leaveReview(reviewWrapper)
}