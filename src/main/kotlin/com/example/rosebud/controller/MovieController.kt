package com.example.rosebud.controller

import com.example.rosebud.model.Movie
import com.example.rosebud.model.wrapper.MovieRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
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

    @GetMapping("/statsForUser/{username}")
    fun getStatsForUser(@PathVariable username: String) = this.movieService.getStatsForUser(username)

    @PostMapping("/create")
    fun createMovie(@RequestBody movieToSave: Movie): Movie = this.movieService.save(movieToSave)

    @PostMapping("/rate")
    fun rateMovie(@RequestBody movieRateWrapper: MovieRateWrapper): Movie = this.movieService.rateMovie(movieRateWrapper)

    @PostMapping("/leaveReview")
    fun leaveReview(@RequestBody reviewWrapper: ReviewWrapper): Movie = this.movieService.leaveReview(reviewWrapper)

    @PostMapping("/addToWachtedList")
    fun addToWachtedList(@RequestBody wachtedListWrapper: WachtedListWrapper): Boolean = this.movieService.addToWachtedList(wachtedListWrapper)
}