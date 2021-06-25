package com.example.rosebud.repository

import com.example.rosebud.model.Movie
import com.example.rosebud.utils.QueryConstants
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface MovieRepository: JpaRepository<Movie, String> {

    fun findByTitleIgnoreCaseContaining(title: String): List<Movie>?

    @Query(value="SELECT movie.director, COUNT(movie.director)" +
                  QueryConstants.QUERY +
                 "GROUP BY movie.director " +
                 "ORDER BY SUM(duration.hours)", nativeQuery = true)
    fun getStatsForUser(username: String): List<Any>

    @Query(value="SELECT SUM(duration.hours)" +
                  QueryConstants.QUERY +
                 "ORDER BY SUM(duration.hours)", nativeQuery = true)
    fun getHoursWatchedForUser(username: String): Int?


    @Query(value="SELECT movie.gender " +
                  QueryConstants.QUERY  +
                 "GROUP BY movie.gender " +
                 "ORDER BY COUNT(movie.gender) " +
                 "LIMIT 3", nativeQuery = true)
    fun getGendersOfUser(username: String): List<String>
}