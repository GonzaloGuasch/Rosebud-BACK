package com.example.rosebud.service.implementations

import com.example.rosebud.model.*
import com.example.rosebud.model.wrapper.ElementRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.StatsWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.example.rosebud.repository.DiskRepository
import com.example.rosebud.repository.MovieRepository
import com.example.rosebud.repository.ReviewRepository
import com.example.rosebud.service.interfaces.IElementService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.File
import java.util.*

@Service
class ElementServiceImpl(private val movieRepository: MovieRepository,
                         private val durationServiceImpl: DurationServiceImpl,
                         private val reviewRepository: ReviewRepository,
                         private val userServiceImpl: UserServiceImpl,
                         private val diskRepository: DiskRepository): IElementService {


    override fun getAllMovies(): List<Movie> = this.movieRepository.findAll()
    override fun getElementsMatchWithTitle(title: String, isDiskQuery: Boolean): List<Element>? {
        if(isDiskQuery) {
            return this.diskRepository.findByTitleIgnoreCaseContaining(title)
        }
        return this.movieRepository.findByTitleIgnoreCaseContaining(title)
    }

    override fun getElementByTitle(elementTitle: String, isDiskQuery: Boolean): Element? {
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

    override fun movieStatsForUser(username: String): StatsWrapper {
        if(username.isEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del usuario no puede ser vacio")
        }
        val listenMinutesList = this.movieRepository.getMinutesWatched(username).stream().reduce{ elem, sum -> elem + sum }.get()
        val listenHoursList = this.movieRepository.getHoursWatchedForUser(username).stream().reduce{ elem, sum -> elem + sum }.get()
        val hours = listenHoursList + (listenMinutesList / 60)

        val gendersWatched = this.movieRepository.getGendersOfUser(username)
        return  StatsWrapper(this.movieRepository.getStatsForUser(username), hours, gendersWatched)
    }

    override fun saveMovieWithoutPicture(movieToSave: Movie): Movie {
        this.durationServiceImpl.save(movieToSave.duration)
        return this.movieRepository.save(movieToSave)
    }

    override fun addImageToMovie(movieImage: MultipartFile, movieTitle: String) {
        try {
            val file = File("C:\\Users\\Gonzalo\\Desktop\\images_tip\\$movieTitle.jpg")
            file.writeBytes(movieImage.bytes)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun rateElement(elementRateWrapper: ElementRateWrapper, isDiskQuery: Boolean): Element {
        if(isDiskQuery) {
            return rateDisk(elementRateWrapper)
        }
        return rateMovie(elementRateWrapper)
    }

    override fun leaveReviewInElement(reviewWrapper: ReviewWrapper, isDiskQuery: Boolean): Element {
        val newReview = Review(reviewWrapper.username, reviewWrapper.review, reviewWrapper.hasSpoilers)
        if(isDiskQuery) {
            return leaveReviewToDisk(reviewWrapper, newReview)
        }
        return leaveReviewMovvie(reviewWrapper, newReview)
    }


    override fun addElementToWatchedList(wachtedListWrapper: WachtedListWrapper, isDiskQuery: Boolean): Boolean {
        val user: User = this.userServiceImpl.getByUsername(wachtedListWrapper.username)
        if(isDiskQuery) {
            val disk = checkEmptydisk(wachtedListWrapper.elementTitle)
            user.addDiskListen(disk)
            this.userServiceImpl.save(user)
            return user.isDiskInList(wachtedListWrapper.elementTitle)
        }
        val movie = checkEmptyMovie(wachtedListWrapper.elementTitle)
        user.addMovieWachted(movie)
        this.userServiceImpl.save(user)
        return user.isMovieInList(wachtedListWrapper.elementTitle)
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

    override fun saveDiskWithoutPicture(diskToSave: Disk): Disk {
        this.durationServiceImpl.save(diskToSave.duration)
        return this.diskRepository.save(diskToSave)
    }

    override fun addImageToDisk(diskImage: MultipartFile, diskTitle: String): Disk {
        return this.diskRepository.findById(diskTitle).get()
    }

    override fun getAllDisks(): List<Disk> = this.diskRepository.findAll()

    override fun diskStatsForUser(username: String): StatsWrapper {
        if(username.isEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del usuario no puede ser vacio")
        }
        val listenMinutesList = this.diskRepository.getMinutesListen(username).stream().reduce{elem, sum -> elem + sum }.get()
        val listenHoursList = this.diskRepository.getHoursListen(username).stream().reduce{elem, sum -> elem + sum }.get()
        val hours = listenHoursList + (listenMinutesList / 60)
        val gendersListen = this.diskRepository.getGendersOfUser(username)
        return  StatsWrapper(this.diskRepository.getStatsForUser(username), hours, gendersListen)
    }

}