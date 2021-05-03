package com.example.rosebud.model

import javax.persistence.*

@Entity(name="users")
class User(@Id val username: String,
               val password: String,
           @ManyToMany
           var moviesWatched: MutableSet<Movie> = mutableSetOf()) {


    fun addMovieWachted(movieWatched: Movie) {
        this.moviesWatched.add(movieWatched)
    }
}