package com.example.rosebud.repository

import com.example.rosebud.model.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository: JpaRepository<Movie, String> {

    fun findByTitleIgnoreCaseContaining(title: String): List<Movie>?

    @Query(value="SELECT movie.director, COUNT(movie.director) " +
                 "FROM movie " +
                 "INNER JOIN duration ON movie.duration_id = duration.id " +
                 "WHERE movie.title IN (SELECT users_movies_watched.movies_watched_title " +
                                        "FROM users_movies_watched " +
                                        "WHERE users_movies_watched.users_username = ?1) " +
                 "GROUP BY movie.director " +
                 "ORDER BY SUM(duration.hours)", nativeQuery = true)
    fun getStatsForUser(username: String):  List<Any>

    @Query(value="SELECT SUM(duration.hours)" +
                 "FROM movie " +
                 "INNER JOIN duration ON movie.duration_id = duration.id " +
                 "WHERE movie.title IN " +
                                     "(SELECT users_movies_watched.movies_watched_title "+
                                     "FROM users_movies_watched "
                                    +"WHERE users_movies_watched.users_username = ?1) " +
                 "ORDER BY SUM(duration.hours)", nativeQuery = true)
    fun getHoursWatchedForUser(username: String): Int
}