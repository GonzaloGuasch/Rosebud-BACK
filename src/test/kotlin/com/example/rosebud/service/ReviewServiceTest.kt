package com.example.rosebud.service
import com.example.rosebud.model.Duration
import com.example.rosebud.model.Movie
import com.example.rosebud.model.Review
import com.example.rosebud.model.exception.NoElementException
import com.example.rosebud.model.exception.NoReviewException
import com.example.rosebud.repository.ReviewRepository
import com.example.rosebud.service.implementations.ElementServiceImpl
import com.example.rosebud.service.implementations.ReviewServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import java.lang.RuntimeException
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewServiceTest {


    private var reviewRepository = Mockito.mock(ReviewRepository::class.java)
    private var elementServiceImpl = Mockito.mock(ElementServiceImpl::class.java)
    private var reviewServiceImpl = ReviewServiceImpl(reviewRepository, elementServiceImpl)
    private var review = Review("USER_TEST", "REVIEW TEST", false)
    private var movie = Movie("James cameron", "", 1994,"Action", "The terminator", Duration(2, 30))

    @BeforeAll
    fun setUp() {
        Mockito.`when`(reviewRepository.findById(0)).thenReturn(Optional.empty())
        Mockito.`when`(reviewRepository.findById(1)).thenReturn(Optional.of(review))
        Mockito.`when`(elementServiceImpl.getElementByTitle("Null element")).thenReturn(null)
        Mockito.`when`(elementServiceImpl.getElementByTitle("Movie_title")).thenReturn(movie)
        Mockito.`when`(elementServiceImpl.saveMovieWithoutPicture(movie)).thenReturn(movie)
    }

    @Test
    fun test001_() {
        Assertions.assertThrows(NoReviewException::class.java) { reviewServiceImpl.deleteReview("0", "Null element") }
    }

    @Test
    fun test002_() {
        Assertions.assertThrows(NoElementException::class.java) { reviewServiceImpl.deleteReview("1", "Null element") }
    }

    @Test
    fun test003_() {
        Assertions.assertThrows(RuntimeException::class.java) { reviewServiceImpl.deleteReview("1", "Movie_title") }
    }

    @Test
    fun test004_() {
        movie.addReview(review)
        reviewServiceImpl.deleteReview("1", "Movie_title")

        Assertions.assertEquals(0, movie.reviews.size)
    }
}












