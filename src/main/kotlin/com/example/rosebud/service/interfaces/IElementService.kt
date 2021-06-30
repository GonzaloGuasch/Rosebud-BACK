package com.example.rosebud.service.interfaces

import com.example.rosebud.model.Disk
import com.example.rosebud.model.Element
import com.example.rosebud.model.Movie
import com.example.rosebud.model.wrapper.ElementRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.StatsWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import org.springframework.web.multipart.MultipartFile

interface IElementService {
    fun rateElement(elementRateWrapper: ElementRateWrapper, isDiskQuery: Boolean = false): Element
    fun leaveReviewInElement(reviewWrapper: ReviewWrapper, isDiskQuery: Boolean = false): Element
    fun addElementToWatchedList(wachtedListWrapper: WachtedListWrapper, isDiskQuery: Boolean = false): Boolean
    fun getElementByTitle(elementTitle: String, isDiskQuery: Boolean = false): Element?
    fun getElementsMatchWithTitle(title: String, isDiskQuery: Boolean = false): List<Element>?

    fun saveMovieWithoutPicture(movieToSave: Movie): Movie
    fun addImageToMovie(movieImage: MultipartFile, movieTitle: String)
    fun getAllMovies(): List<Movie>
    fun movieStatsForUser(username: String): StatsWrapper

    fun saveDiskWithoutPicture(diskToSave: Disk): Disk
    fun addImageToDisk(diskImage: MultipartFile, diskTitle: String): Disk
    fun getAllDisks(): List<Disk>
    fun diskStatsForUser(username: String): StatsWrapper
}