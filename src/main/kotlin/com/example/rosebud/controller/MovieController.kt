package com.example.rosebud.controller

import com.example.rosebud.model.Element
import com.example.rosebud.model.Movie
import com.example.rosebud.model.wrapper.ElementRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.example.rosebud.service.ElementService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/movie")
class MovieController(private val elementService: ElementService) {

    @GetMapping("/")
    fun getAllMovies(): List<Movie>? = this.elementService.getAllMovies()

    @PostMapping("/create")
    fun createMovie(@RequestBody movieToSave: Movie): Movie =  this.elementService.saveMovieWithoutPicture(movieToSave)

    @PostMapping("/addImage/{movieTitle}")
    fun addImageToMovie(@RequestParam("movieImage") movieImage: MultipartFile,
                        @PathVariable movieTitle: String) = this.elementService.addImageToMovie(movieImage, movieTitle)

    @GetMapping("/searchByTitle/{movieTitle}")
    fun getMovieByTitle(@PathVariable movieTitle: String): List<Element>? = this.elementService.getElementsMatchWithTitle(movieTitle)

    @GetMapping("/getByTitle/{movieTitle}")
    fun getByTitle(@PathVariable movieTitle: String): Element? = this.elementService.getElementByTitle(movieTitle)

    @GetMapping("/statsForUser/{username}")
    fun getStatsForUser(@PathVariable username: String) = this.elementService.movieStatsForUser(username)

    @PostMapping("/rate")
    fun rateMovie(@RequestBody elementRateWrapper: ElementRateWrapper): Element = this.elementService.rateElement(elementRateWrapper)

    @PostMapping("/leaveReview")
    fun leaveReview(@RequestBody reviewWrapper: ReviewWrapper): Element = this.elementService.leaveReviewInElement(reviewWrapper)

    @PostMapping("/addToWachtedList")
    fun addToWachtedList(@RequestBody wachtedListWrapper: WachtedListWrapper): Boolean = this.elementService.addMovieToWatchedList(wachtedListWrapper)
}

