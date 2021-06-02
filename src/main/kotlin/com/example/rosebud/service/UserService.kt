package com.example.rosebud.service

import com.example.rosebud.model.User
import com.example.rosebud.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User>? = this.userRepository.findAll()
    fun save(user: User): User = this.userRepository.save(user)
    fun getByUsername(username: String): User = this.userRepository.findById(username).get()

    fun userInfo(username: String): Int {
     val user = this.userRepository.findById(username).get()
     return user.moviesWatched.size
    }

    fun isMovieInList(movieTitle: String, username: String): Boolean {
        val username = this.userRepository.findById(username).get()
        return username.isMovieInList(movieTitle)
    }

    fun getDatavisitProfile(username: String): Int {
        val username = this.userRepository.findById(username).get()
        return username.moviesWatched.size
    }
}