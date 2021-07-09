package com.example.rosebud.utils

object QueryConstants {
    const val QUERY =  "FROM movie " +
                             "INNER JOIN duration ON movie.duration_id = duration.id " +
                             "WHERE movie.title IN " +
                                                  "(SELECT users_movies_watched.movies_watched " +
                                                  "FROM users_movies_watched " +
                                                  "WHERE users_movies_watched.users_username = ?1) "

    const val QUERY_DISK =  "FROM disk " +
                            "INNER JOIN duration ON disk.duration_id = duration.id " +
                            "WHERE disk.title IN " +
                                                "(SELECT users_disk_listen.disk_listen " +
                                                "FROM users_disk_listen " +
                                                "WHERE users_disk_listen.users_username = ?1) "

}