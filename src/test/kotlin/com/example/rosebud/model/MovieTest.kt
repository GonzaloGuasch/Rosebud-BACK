package com.example.rosebud.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class MovieTest {

    @Test
    fun test_001_aMovieHasCeroStartUntilAUserRatesIt() {
        val movie = Movie("The terminator", "James cameron")
        assertEquals(0, movie.raiting)
        movie.rate(3)
        assertEquals(3, movie.raiting)
    }

    @Test
    fun test_002_IfMoreThanAUserVoteTheSameMovieTheRateIsTheAverage() {
        val movie = Movie("The terminator", "James cameron")
        movie.rate(5)
        movie.rate(1)
        assertNotEquals(1, movie.raiting)
        assertEquals(3, movie.raiting)
    }

    @Test
    fun test_003_AMovieraitingCannotBeHigherThanFive() {
        val movie = Movie("The terminator", "James cameron")
        movie.rate(5)
        movie.rate(5)
        movie.rate(5)
        assertEquals(5, movie.raiting)
    }

    @Test
    fun test_004_UsersCanLeaveReviewsInMovies() {
        val movie = Movie("Alien: the eight passanger", "Ridly Scott")
        val review = Review("user_test", "The best alien movie ever!")
        movie.addReview(review)

        assertEquals(1, movie.reviews.size)
    }
}