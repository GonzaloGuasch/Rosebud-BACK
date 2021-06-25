package com.example.rosebud.service

import com.example.rosebud.model.wrapper.Seguidores
import com.example.rosebud.model.User
import com.example.rosebud.model.wrapper.UserInfoProfile
import com.example.rosebud.repository.ReviewRepository
import com.example.rosebud.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@Transactional
class UserService(private val userRepository: UserRepository,
                  private val reviewRepository: ReviewRepository) {

    fun getAllUsers(): List<User>? = this.userRepository.findAll()
    fun save(user: User): User = this.userRepository.save(user)
    fun getByUsername(username: String): User = this.userRepository.findById(username).get()

    fun userInfoProfile(username: String): UserInfoProfile {
     val user = this.userRepository.findById(username).get()

     return UserInfoProfile(user.moviesWatched.size, user.followers.size, user.following.size)
    }

    fun isMovieInList(movieTitle: String, username: String): Boolean {
        val username = this.userRepository.findById(username).get()
        return username.isMovieInList(movieTitle)
    }

    fun getDatavisitProfile(username: String): Long {
        val user = this.userRepository.findById(username).get()
        return this.reviewRepository.findAll()
                                    .stream()
                                    .filter { aReview -> aReview.userCreate.equals(user.username) }
                                    .count()
    }

    fun userFollowUSer(firstUser: String, secondUser: String): Boolean {
        val secondUser = this.userRepository.findById(secondUser).get()
        return secondUser.isFollowedBy(firstUser);
    }

    fun follow(userToFollo: String, userFollower: String) {
        val userToFollo = this.userRepository.findById(userToFollo).get()
        val userFollower = this.userRepository.findById(userFollower).get()
        userFollower.follow(userToFollo)

        this.userRepository.save(userFollower)
        this.userRepository.save(userToFollo)
    }

    fun unfollow(userFollower: String, userToUnfollow: String) {
        val userFollowed = this.userRepository.findById(userToUnfollow).get()
        val userFollowing = this.userRepository.findById(userFollower).get()
        userFollowing.unfollow(userFollowed)

        this.userRepository.save(userFollowed)
        this.userRepository.save(userFollowing)
    }

    fun seguidoresDe(username: String): List<Seguidores> {
        val user = this.userRepository.findById(username).get()
        return user.followers.stream()
                             .map { aUserFollower -> Seguidores(aUserFollower.username) }
                             .collect(Collectors.toList())
    }

    fun seguidosDe(username: String): List<Seguidores> {
        val user = this.userRepository.findById(username).get()
        return user.following.stream()
                .map { aUserFollower -> Seguidores(aUserFollower.username) }
                .collect(Collectors.toList())
    }
}