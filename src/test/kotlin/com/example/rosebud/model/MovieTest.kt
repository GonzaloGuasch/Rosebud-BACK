package com.example.rosebud.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class MovieTest {
    private lateinit var movie: Movie

    @BeforeEach
    fun setUp() {
        movie = Movie("James cameron", "", 1994,"Action", "The terminator", Duration(2, 30))
    }

    @Test
    fun test_001_aMovieHasCeroStartUntilAUserRatesIt() {
        assertEquals(0, movie.raiting)
        movie.rate(3)
        assertEquals(3, movie.raiting)
    }

    @Test
    fun test_002_IfMoreThanAUserVoteTheSameMovieTheRateIsTheAverage() {
        movie.rate(5)
        movie.rate(1)
        assertNotEquals(1, movie.raiting)
        assertEquals(3, movie.raiting)
    }

    @Test
    fun test_003_AMovieraitingCannotBeHigherThanFive() {
        movie.rate(5)
        movie.rate(5)
        movie.rate(5)
        assertEquals(5, movie.raiting)
    }

    @Test
    fun test_004_UsersCanLeaveReviewsInMovies() {
        val review = Review("user_test", "The best alien movie ever!", false)
        movie.addReview(review)

        assertEquals(1, movie.reviews.size)
    }
}