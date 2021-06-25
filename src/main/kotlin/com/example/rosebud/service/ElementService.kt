package com.example.rosebud.service

import com.example.rosebud.model.*
import com.example.rosebud.model.wrapper.ElementRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.StatsWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.example.rosebud.repository.DiskRepository
import com.example.rosebud.repository.MovieRepository
import com.example.rosebud.repository.ReviewRepository
import com.example.rosebud.service.implementations.DurationServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class ElementService(private val movieRepository: MovieRepository,
                     private val durationServiceImpl: DurationServiceImpl,
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
            if(diskResult.isEmpty) {
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
        this.durationServiceImpl.save(movieToSave.duration)
        return this.movieRepository.save(movieToSave)
    }

    fun addImageToMovie(movieImage: MultipartFile, movieTitle: String): Movie {
        val movie =  this.movieRepository.findById(movieTitle).get()
        movie.imagen = movieImage.bytes
        return this.movieRepository.save(movie)
    }

    fun rateElement(elementRateWrapper: ElementRateWrapper, isDiskQuery: Boolean = false): Element {
        if(isDiskQuery) {
            return rateDisk(elementRateWrapper)
        }
        return rateMovie(elementRateWrapper)
    }

    fun leaveReviewInElement(reviewWrapper: ReviewWrapper, isDiskQuery: Boolean = false): Element {
        val newReview = Review(reviewWrapper.username, reviewWrapper.review, reviewWrapper.hasSpoilers)
        if(isDiskQuery) {
            return leaveReviewToDisk(reviewWrapper, newReview)
        }
        return leaveReviewMovvie(reviewWrapper, newReview)
    }


    fun addElementToWatchedList(wachtedListWrapper: WachtedListWrapper, isDiskQuery: Boolean = false): Boolean {
        val user: User = this.userService.getByUsername(wachtedListWrapper.username)
        if(isDiskQuery) {
            val disk = checkEmptydisk(wachtedListWrapper.elementTitle)
            user.addDiskListen(disk)
        } else {
            val movie = checkEmptyMovie(wachtedListWrapper.elementTitle)
            user.addMovieWachted(movie)
        }
        this.userService.save(user)

        return true
    }

    private fun rateMovie(elementRateWrapper: ElementRateWrapper): Movie {
        val movieToRate = this.checkEmptyMovie(elementRateWrapper.elementTitle)
        movieToRate.rate(elementRateWrapper.rate)
        return this.movieRepository.save(movieToRate)
    }

    private fun leaveReviewMovvie(reviewWrapper: ReviewWrapper, newReview: Review): Movie {
        val movieToReview = checkEmptyMovie(reviewWrapper.elementTitle)
        movieToReview.addReview(newReview)
        return this.movieRepository.save(movieToReview)
    }

    private fun checkEmptyMovie(elementTitle: String): Movie {
        val optionalMovie: Optional<Movie> = this.movieRepository.findById(elementTitle)
        if (optionalMovie.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la pelicula con ese titulo")
        }
        return optionalMovie.get()
    }

    // DISK SERVICE
    private fun rateDisk(elementRateWrapper: ElementRateWrapper): Disk {
        val diskToRate = this.checkEmptydisk(elementRateWrapper.elementTitle)
        diskToRate.rate(elementRateWrapper.rate)
        return this.diskRepository.save(diskToRate)
    }
    private fun leaveReviewToDisk(reviewWrapper: ReviewWrapper, newReview: Review): Disk {
        val diskToReview: Disk = checkEmptydisk(reviewWrapper.elementTitle)
        diskToReview.addReview(newReview)
        this.reviewRepository.save(newReview)
        return this.diskRepository.save(diskToReview)
    }

    private fun checkEmptydisk(elementTitle: String): Disk {
        val diskToReviewOptional: Optional<Disk> = this.diskRepository.findById(elementTitle)
        if (diskToReviewOptional.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe el disco con ese titulo")
        }
        return diskToReviewOptional.get()
    }

    fun saveDiskWithoutPicture(diskToSave: Disk): Disk {
        this.durationServiceImpl.save(diskToSave.duration)
        return this.diskRepository.save(diskToSave)
    }

    fun addImageToDisk(diskImage: MultipartFile, diskTitle: String): Disk {
        val disk =  this.diskRepository.findById(diskTitle).get()
        disk.imagen = diskImage.bytes
        return this.diskRepository.save(disk)
    }

    fun getAllDisks(): List<Disk> = this.diskRepository.findAll()

}