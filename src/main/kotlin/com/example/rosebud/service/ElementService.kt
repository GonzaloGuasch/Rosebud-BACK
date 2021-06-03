package com.example.rosebud.service

import com.example.rosebud.model.*
import com.example.rosebud.model.wrapper.ElementRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.StatsWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.example.rosebud.repository.DiskRepository
import com.example.rosebud.repository.MovieRepository
import com.example.rosebud.repository.ReviewRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class ElementService(private val movieRepository: MovieRepository,
                     private val durationService: DurationService,
                     private val reviewRepository: ReviewRepository,
                     private val userService: UserService,
                     private val diskRepository: DiskRepository) {


    fun getAllMovies(): List<Movie> = this.movieRepository.findAll()


    fun getElementsMatchWithTitle(title: String, isDiskQuery: Boolean = false): List<Element>? {
        if(isDiskQuery) {
            return this.diskRepository.findByTitleIgnoreCaseContaining(title)
        }
        return this.movieRepository.findByTitleIgnoreCaseContaining(title)
    }

    fun getElementByTitle(elementTitle: String, isDiskQuery: Boolean = false): Element? {
        if(isDiskQuery) {
            val diskResult = this.diskRepository.findById(elementTitle)
            if(diskResult.isEmpty){
                return null
            }
            return diskResult.get()
        } else {
            val movieResult = this.movieRepository.findById(elementTitle)
            if(movieResult.isEmpty){
                return null
            }
            return movieResult.get()
        }

    }

    fun movieStatsForUser(username: String): StatsWrapper {
        if(username.isEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del usuario no puede ser vacio")
        }
        val hoursWatched = this.movieRepository.getHoursWatchedForUser(username) ?: 0
        val gendersWatched = this.movieRepository.getGendersOfUser(username)
        return  StatsWrapper(this.movieRepository.getStatsForUser(username), hoursWatched, gendersWatched)
    }

    fun saveMovieWithoutPicture(movieToSave: Movie): Movie {
        this.durationService.save(movieToSave.duration)
        return this.movieRepository.save(movieToSave)
    }

    fun addImageToMovie(movieImage: MultipartFile, movieTitle: String): Movie {
        val movie =  this.movieRepository.findById(movieTitle).get()
        movie.imagen = movieImage.bytes
        return this.movieRepository.save(movie)
    }

    fun rateElement(elementRateWrapper: ElementRateWrapper, isDiskQuery: Boolean = false): Element {
        if(isDiskQuery) {
            val diskToRateOptional: Optional<Disk> = this.diskRepository.findById(elementRateWrapper.elementTitle)
            if(diskToRateOptional.isEmpty) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe el disco con ese titulo")
            }
            val diskToRate = diskToRateOptional.get()
            diskToRate.rate(elementRateWrapper.rate)
            return this.diskRepository.save(diskToRate)
        }
        val movieToRateOptional: Optional<Movie> = this.movieRepository.findById(elementRateWrapper.elementTitle)
        if(movieToRateOptional.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la pelicula con ese titulo")
        }
        val movieToRate = movieToRateOptional.get()
        movieToRate.rate(elementRateWrapper.rate)
        return this.movieRepository.save(movieToRate)
    }

    fun leaveReviewInElement(reviewWrapper: ReviewWrapper, isDiskQuery: Boolean = false): Element {
        val newReview = Review(reviewWrapper.username, reviewWrapper.review, reviewWrapper.hasSpoilers)
        if(isDiskQuery) {
            val diskToReviewOptional: Optional<Disk> = this.diskRepository.findById(reviewWrapper.elementTitle)
            if(diskToReviewOptional.isEmpty) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe el disco con ese titulo")
            }
            val diskToReview = diskToReviewOptional.get()
            diskToReview.addReview(newReview)
            this.reviewRepository.save(newReview)
            return this.diskRepository.save(diskToReview)
        }
        val movieToReviewOptional: Optional<Movie> = this.movieRepository.findById(reviewWrapper.elementTitle)
        if(movieToReviewOptional.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la pelicula con ese titulo")
        }
        val movieToReview = movieToReviewOptional.get()
        movieToReview.addReview(newReview)
        return this.movieRepository.save(movieToReview)
    }

    fun addMovieToWatchedList(wachtedListWrapper: WachtedListWrapper): Boolean {
        val optionalMovie:  Optional<Movie> = this.movieRepository.findById(wachtedListWrapper.elementTitle)
        if(optionalMovie.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la pelicula con ese titulo")
        }
        val movie = optionalMovie.get()
        val user: User = this.userService.getByUsername(wachtedListWrapper.username)
        if(user.isMovieInList(movie.title)) {
            user.moviesWatched.remove(movie)
            this.userService.save(user)
            return false
        }
        user.addMovieWachted(movie)
        this.userService.save(user)
        return true
    }



    // DISK SERVICE
    fun saveDiskWithoutPicture(diskToSave: Disk): Disk = this.diskRepository.save(diskToSave)
    fun getAllDisks(): List<Disk> = this.diskRepository.findAll()

    fun addImageToDisk(diskImage: MultipartFile, diskTitle: String): Disk {
        val disk =  this.diskRepository.findById(diskTitle).get()
        disk.imagen = diskImage.bytes
        return this.diskRepository.save(disk)
    }

}