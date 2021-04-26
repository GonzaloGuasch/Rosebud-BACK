package com.example.rosebud.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
class User(@Id val username: String,
               val password: String,
          @ManyToMany
          var moviesWatched: MutableSet<Movie> = mutableSetOf()) {


    fun addMovieWachted(movieWatched: Movie) {
        this.moviesWatched.add(movieWatched)
    }
}