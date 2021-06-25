package com.example.rosebud.model

import com.example.rosebud.model.exception.NoFollowerException
import javax.persistence.*

@Entity(name="users")
class User(@Id val username: String,
               val password: String,
               val email: String,
           @ManyToMany
           var followers: MutableSet<User> = mutableSetOf(),
           @ManyToMany
           var following: MutableSet<User> = mutableSetOf(),
           @ManyToMany
           var moviesWatched: MutableSet<Movie> = mutableSetOf(),
           @ManyToMany
           var diskListen: MutableSet<Disk> = mutableSetOf()) {


    fun addMovieWachted(movieWatched: Movie) {
        if(this.moviesWatched.contains(movieWatched)) {
            this.moviesWatched.remove(movieWatched)
            return
        }
        this.moviesWatched.add(movieWatched)
    }

    fun addDiskListen(diskListen: Disk) {
        if(this.diskListen.contains(diskListen)) {
            this.diskListen.remove(diskListen)
            return
        }
        this.diskListen.add(diskListen)
    }

    fun isMovieInList(movieTitle: String): Boolean {
        return this.moviesWatched.any { aMovie -> aMovie.title == movieTitle }
    }

    fun isFollowedBy(userFollower: String): Boolean {
        return this.followers.any { aFollower -> aFollower.username.equals(userFollower) }
    }

    fun follow(userToFollo: User) {
        this.following.add(userToFollo)
        userToFollo.followers.add(this)
    }

    fun unfollow(userToUnfollow: User) {
        if(isFollowing(userToUnfollow)) {
            this.following.remove(userToUnfollow)
            userToUnfollow.followers.remove(this)
            return
        }
        throw NoFollowerException("El usuario no esta siguiendolo!")
    }

    private fun isFollowing(userToUnfollow: User) = this.following.contains(userToUnfollow)

}