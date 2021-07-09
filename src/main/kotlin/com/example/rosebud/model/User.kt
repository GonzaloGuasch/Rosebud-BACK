package com.example.rosebud.model

import com.example.rosebud.model.exception.NoFollowerException
import javax.persistence.*

@Entity(name="users")
open class User(@Id open val username: String,
                open val password: String,
                open val email: String,
                @ManyToMany
                open var followers: MutableSet<User> = mutableSetOf(),
                @ManyToMany
                open var following: MutableSet<User> = mutableSetOf(),
                @ElementCollection
                open var moviesWatched: MutableSet<String> = mutableSetOf(),
                @ElementCollection
                open var diskListen: MutableSet<String> = mutableSetOf()) {


    fun addMovieWachted(movieWatched: String) {
        if(this.moviesWatched.contains(movieWatched)) {
            this.moviesWatched.remove(movieWatched)
            return
        }
        this.moviesWatched.add(movieWatched)
    }

    fun addDiskListen(diskListen: String) {
        if(this.diskListen.contains(diskListen)) {
            this.diskListen.remove(diskListen)
            return
        }
        this.diskListen.add(diskListen)
    }

    fun isMovieInList(movieTitle: String): Boolean {
        return this.moviesWatched.any { aMovieTitle -> aMovieTitle == movieTitle }
    }

    fun isDiskInList(diskTitle: String): Boolean {
        return this.diskListen.any { aDiskTitle -> aDiskTitle == diskTitle }
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