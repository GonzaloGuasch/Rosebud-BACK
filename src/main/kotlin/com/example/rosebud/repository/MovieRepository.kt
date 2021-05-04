package com.example.rosebud.repository

import com.example.rosebud.model.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository: JpaRepository<Movie, String> {

    fun findByTitleIgnoreCaseContaining(title: String): List<Movie>?

    @Query(value="SELECT movie.director, count(movie.director) FROM movie " +
                 "WHERE movie.title IN (SELECT users_movies_watched.movies_watched_title " +
                                        "FROM users_movies_watched " +
                                        "WHERE users_movies_watched.users_username = ?1) " +
                 "GROUP BY movie.director", nativeQuery = true)
    fun getStatsForUser(username: String):  Any
}