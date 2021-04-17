package com.example.rosebud.controller

import com.example.rosebud.model.Movie
import com.example.rosebud.repository.MovieRepository
import com.example.rosebud.service.MovieService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/movie")
@CrossOrigin(origins = ["http://localhost:3000"])
class MovieController(private var movieService: MovieService) {

    @GetMapping("/")
    fun getAllMovies(): List<Movie>? = this.movieService.getAllMovies()

    @PostMapping("/create")
    fun createMovie(@RequestBody movieToSave: Movie): Movie = this.movieService.save(movieToSave)

    @GetMapping("searchByTitle/{movieTitle}")
    fun getMovieByTitle(@PathVariable movieTitle: String): List<Movie>? = this.movieService.getMovieByTitle(movieTitle)
}