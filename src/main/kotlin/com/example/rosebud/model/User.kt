package com.example.rosebud.model

import javax.persistence.*

@Entity(name="users")
class User(@Id val username: String,
               val password: String,
           @ManyToMany
           var followers: MutableSet<User> = mutableSetOf(),
           @ManyToMany
           var following: MutableSet<User> = mutableSetOf(),
           @ManyToMany
           var moviesWatched: MutableSet<Movie> = mutableSetOf()) {


    fun addMovieWachted(movieWatched: Movie) {
        this.moviesWatched.add(movieWatched)
    }

    fun isMovieInList(movieTitle: String): Boolean {
        return this.moviesWatched.any { aMovie -> aMovie.title == movieTitle }
    }

    fun isFollowedBy(userFollower: String): Boolean {
        return this.followers.any {aFollower -> aFollower.username.equals(userFollower) }
    }

    fun follow(userToFollo: User) {
        this.following.add(userToFollo)
        userToFollo.followers.add(this)
    }

    fun unfollow(userToFollo: User) {
        this.following.remove(userToFollo)
        userToFollo.followers.remove(this)
    }
}