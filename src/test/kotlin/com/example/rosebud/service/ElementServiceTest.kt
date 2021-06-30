package com.example.rosebud.service

import com.example.rosebud.model.*
import com.example.rosebud.model.wrapper.ElementRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.example.rosebud.repository.DiskRepository
import com.example.rosebud.repository.MovieRepository
import com.example.rosebud.repository.ReviewRepository
import com.example.rosebud.service.implementations.DurationServiceImpl
import com.example.rosebud.service.implementations.ElementServiceImpl
import com.example.rosebud.service.implementations.UserServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Matchers
import org.mockito.Mockito
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ElementServiceTest {

    private val movieRepository = Mockito.mock(MovieRepository::class.java)
    private val diskRepository = Mockito.mock(DiskRepository::class.java)
    private val durationServiceImpl = Mockito.mock(DurationServiceImpl::class.java)
    private val reviewRepository = Mockito.mock(ReviewRepository::class.java)
    private val userServiceImpl = Mockito.mock(UserServiceImpl::class.java)
    private val elementServiceImpl = ElementServiceImpl(movieRepository, durationServiceImpl, reviewRepository, userServiceImpl, diskRepository)

    private val errorElementName = "NOT_ELEMENT"
    private val elementName = "ELEMENT"
    private val username = "USER"
    private val disk = Mockito.mock(Disk::class.java)
    private val movie = Mockito.mock(Movie::class.java)
    private val duration = Mockito.mock(Duration::class.java)
    private val user = User(username, "12345", "gmail")

    private val elementWrapperError = ElementRateWrapper(errorElementName, 1)
    private val elementWrapperElement = ElementRateWrapper(elementName, 5)
    private val elementWrapperList = WachtedListWrapper(username, elementName)
    private val reviewWrapper = ReviewWrapper(elementName, username, "REVIEW TEST", false)

    private val review = Review(reviewWrapper.username, reviewWrapper.review, reviewWrapper.hasSpoilers)

    @BeforeAll
    fun setUp() {

        Mockito.`when`(diskRepository.findByTitleIgnoreCaseContaining("DISK")).thenReturn(listOf(disk))
        Mockito.`when`(diskRepository.findById(errorElementName)).thenReturn(Optional.empty())
        Mockito.`when`(diskRepository.findById(elementName)).thenReturn(Optional.of(disk))
        Mockito.`when`(diskRepository.findAll()).thenReturn(listOf(disk))
        Mockito.`when`(diskRepository.save(disk)).thenReturn(disk)
        Mockito.`when`(diskRepository.getMinutesListen(username)).thenReturn(listOf(1, 30, 28))
        Mockito.`when`(diskRepository.getHoursListen(username)).thenReturn(listOf(2, 3, 7))
        Mockito.`when`(diskRepository.getGendersOfUser(username)).thenReturn(listOf("ROCK"))
        Mockito.`when`(diskRepository.getStatsForUser(username)).thenReturn(listOf())

        Mockito.`when`(movieRepository.findByTitleIgnoreCaseContaining("MOVIE")).thenReturn(listOf(movie))

        Mockito.`when`(movieRepository.getMinutesWatched(username)).thenReturn(listOf(1, 30, 28))
        Mockito.`when`(movieRepository.getHoursWatchedForUser(username)).thenReturn(listOf(2, 3, 7))
        Mockito.`when`(movieRepository.getGendersOfUser(username)).thenReturn(listOf("ROCK"))
        Mockito.`when`(movieRepository.getStatsForUser(username)).thenReturn(listOf())
        Mockito.`when`(movieRepository.save(movie)).thenReturn(movie)
        Mockito.`when`(movieRepository.findAll()).thenReturn(listOf(movie))
        Mockito.`when`(movieRepository.findById(errorElementName)).thenReturn(Optional.empty())
        Mockito.`when`(movieRepository.findById(elementName)).thenReturn(Optional.of(movie))

        Mockito.`when`(durationServiceImpl.save(duration)).thenReturn(duration)

        Mockito.`when`(movie.title).thenReturn("MOVIE")
        Mockito.`when`(movie.reviews).thenReturn(mutableSetOf(review))
        Mockito.`when`(movie.addReview(review)).thenReturn(true)

        Mockito.`when`(disk.reviews).thenReturn(mutableSetOf(review))

        Mockito.`when`(userServiceImpl.getByUsername("USER")).thenReturn(user)
        Mockito.`when`(userServiceImpl.save(user)).thenReturn(user)

    }

    @Test
    fun test001_() {
        val  listOfMovies = this.elementServiceImpl.getElementsMatchWithTitle("MOVIE")
        val listOfDisks = this.elementServiceImpl.getElementsMatchWithTitle("DISK", true)

        Assertions.assertEquals(1, listOfMovies!!.size)
        Assertions.assertEquals(1, listOfDisks!!.size)
    }

    @Test
    fun test002_() {
        Assertions.assertThrows(RuntimeException::class.java) { elementServiceImpl.movieStatsForUser("") }
    }

    @Test
    fun test003_() {
        val statsWrapper =  elementServiceImpl.movieStatsForUser("USER")

        Assertions.assertEquals(12, statsWrapper.hours)
        Assertions.assertEquals(listOf("ROCK"), statsWrapper.genders)
    }

    @Test
    fun test004_() {
        this.elementServiceImpl.saveMovieWithoutPicture(movie)

        Assertions.assertEquals(1, elementServiceImpl.getAllMovies().size)
    }

    @Test
    fun test005_() {
        Assertions.assertThrows(RuntimeException::class.java) { elementServiceImpl.rateElement(elementWrapperError) }
    }

    @Test
    fun test006_() {
         elementServiceImpl.rateElement(elementWrapperElement)

        Assertions.assertEquals(5, movie.raiting)
    }

    @Test
    fun test007_() {
        Assertions.assertThrows(RuntimeException::class.java) { elementServiceImpl.rateElement(elementWrapperError, true)}
    }

    @Test
    fun test008_() {
        elementServiceImpl.rateElement(elementWrapperElement, true)

        Assertions.assertEquals(5, disk.raiting)
    }

    @Test
    fun test009_() {
        this.elementServiceImpl.saveDiskWithoutPicture(disk)

        Assertions.assertEquals(1, elementServiceImpl.getAllDisks().size)
    }

    @Test
    fun test010_() {
        Assertions.assertThrows(RuntimeException::class.java) { elementServiceImpl.diskStatsForUser("") }
    }

    @Test
    fun test011_() {
        val statsWrapper =  elementServiceImpl.diskStatsForUser("USER")

        Assertions.assertEquals(12, statsWrapper.hours)
        Assertions.assertEquals(listOf("ROCK"), statsWrapper.genders)
    }

    @Test
    fun test012_() {
        Assertions.assertFalse(elementServiceImpl.addElementToWatchedList(elementWrapperList))
    }

    @Test
    fun test013_() {
        Assertions.assertFalse(elementServiceImpl.addElementToWatchedList(elementWrapperList, true))
    }

    @Test
    fun test014_() {
        elementServiceImpl.leaveReviewInElement(reviewWrapper)

        Assertions.assertEquals( 1, movie.reviews.size)
    }

    @Test
    fun test015_() {
        elementServiceImpl.leaveReviewInElement(reviewWrapper, true)

        Assertions.assertEquals( 1, disk.reviews.size)
    }

}



















