package com.example.rosebud.utils

object QueryConstants {
    const val QUERY =  "FROM movie " +
                             "INNER JOIN duration ON movie.duration_id = duration.id " +
                             "WHERE movie.title IN " +
                                                  "(SELECT users_movies_watched.movies_watched_title " +
                                                  "FROM users_movies_watched " +
                                                  "WHERE users_movies_watched.users_username = ?1) "

}