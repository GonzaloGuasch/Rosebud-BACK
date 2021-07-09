package com.example.rosebud.service.interfaces

import com.example.rosebud.model.Review
import com.example.rosebud.model.User
import com.example.rosebud.model.UserDTO
import com.example.rosebud.model.wrapper.LoginWrapper
import com.example.rosebud.model.wrapper.Seguidores
import com.example.rosebud.model.wrapper.UserInfoProfile


interface IUserService {

    fun getAllUsers(): List<User>?

    fun getByUsername(username: String): User
    fun userInfoProfile(username: String): UserInfoProfile
    fun isMovieInList(movieTitle: String, username: String): Boolean
    fun isDiskInList(diskTitle: String, username: String): Any

    fun unfollow(userFollower: String, userToUnfollow: String)
    fun follow(userToFollo: String, userFollower: String)
    fun save(user: User): User

    fun getDatavisitProfile(username: String): List<Review>
    fun seguidoresDe(username: String): List<Seguidores>
    fun seguidosDe(username: String): List<Seguidores>
    fun userFollowUSer(firstUser: String, secondUser: String): Boolean
    fun login(user: LoginWrapper): UserDTO
}