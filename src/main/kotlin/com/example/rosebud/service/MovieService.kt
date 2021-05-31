package com.example.rosebud.service

import com.example.rosebud.model.Movie
import com.example.rosebud.model.Review
import com.example.rosebud.model.User
import com.example.rosebud.model.wrapper.MovieRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.StatsWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.example.rosebud.repository.MovieRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class MovieService(private val movieRepository: MovieRepository,
                   private val userService: UserService,
                   private val durationService: DurationService) {

    fun getAllMovies(): List<Movie> = this.movieRepository.findAll()

    fun saveWithoutImage(movieToSave: Movie): Movie {
        this.durationService.save(movieToSave.duration)
        return this.movieRepository.save(movieToSave)
    }

    fun addImageToMovie(imagen: MultipartFile, movieId: String): Movie {
        val movie =  this.movieRepository.findById(movieId).get()
        movie.imagen = imagen.bytes
        return this.movieRepository.save(movie)
    }

    fun getMovieByTitle(movieTitle: String): List<Movie>? = this.movieRepository.findByTitleIgnoreCaseContaining(movieTitle)

    fun rateMovie(movieRateWrapper: MovieRateWrapper): Movie {
        val movieToRateOptional: Optional<Movie> = this.movieRepository.findById(movieRateWrapper.movieTitle)
        if(movieToRateOptional.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la pelicula con ese titulo")
        }
        val movieToRate = movieToRateOptional.get()
        movieToRate.rate(movieRateWrapper.rate)
        return this.movieRepository.save(movieToRate)
    }

    fun leaveReview(reviewWrapper: ReviewWrapper): Movie {
        val movieToReviewOptional: Optional<Movie> = this.movieRepository.findById(reviewWrapper.movieTitle)
        val newReview = Review(reviewWrapper.username, reviewWrapper.review, reviewWrapper.hasSpoilers)
        if(movieToReviewOptional.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe la pelicula con ese titulo")
        }
        val movieToReview = movieToReviewOptional.get()
        movieToReview.addReview(newReview)
        return this.movieRepository.save(movieToReview)
    }

    fun getByTitle(movieTitle: String): Movie? {
      val movieResult = this.movieRepository.findById(movieTitle)
      if(movieResult.isEmpty){
          return null
      }
      return movieResult.get()
    }

    fun addToWachtedList(wachtedListWrapper: WachtedListWrapper): Boolean {
        val optionalMovie:  Optional<Movie> = this.movieRepository.findById(wachtedListWrapper.movieTitle)
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

    fun getStatsForUser(username: String): StatsWrapper {
        if(username.isEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del usuario no puede ser vacio")
        }
        val hoursWatched = this.movieRepository.getHoursWatchedForUser(username) ?: 0
        val gendersWatched = this.movieRepository.getGendersOfUser(username)
        return  StatsWrapper(this.movieRepository.getStatsForUser(username), hoursWatched, gendersWatched)
    }

}