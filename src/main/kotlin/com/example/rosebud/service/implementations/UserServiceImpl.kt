package com.example.rosebud.service.implementations

import com.example.rosebud.model.Review
import com.example.rosebud.model.wrapper.Seguidores
import com.example.rosebud.model.User
import com.example.rosebud.model.exception.NoUserException
import com.example.rosebud.model.wrapper.LoginWrapper
import com.example.rosebud.model.wrapper.UserInfoProfile
import com.example.rosebud.repository.ReviewRepository
import com.example.rosebud.repository.UserRepository
import com.example.rosebud.service.interfaces.IUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository,
                      private val reviewRepository: ReviewRepository): IUserService {

    override fun getAllUsers(): List<User>? = this.userRepository.findAll()
    override fun save(user: User): User = this.userRepository.save(user)

    override fun login(user: LoginWrapper): User {
        val optinalUser = this.userRepository.findByEmail(user.email)
        if(optinalUser.isEmpty) {
            throw NoUserException("No hay un usuario con ese username")
        }
        if(optinalUser.get().password.equals(user.password)) {
            return optinalUser.get()
        }
        throw NoUserException("No hay un usuario con ese username")
    }

    override fun getByUsername(username: String): User {
        val optionalUser = this.userRepository.findById(username)
        if(optionalUser.isEmpty) {
            throw NoUserException("No hay un usuario con ese username")
        }
        return optionalUser.get()
    }

    override fun userInfoProfile(username: String): UserInfoProfile {
     val user = this.getByUsername(username)

     return UserInfoProfile(user.moviesWatched.size, user.followers.size, user.following.size)
    }

    override fun isMovieInList(movieTitle: String, username: String): Boolean {
        val user = this.getByUsername(username)
        return user.isMovieInList(movieTitle)
    }

    override fun isDiskInList(diskTitle: String, username: String): Boolean {
        val user = this.getByUsername(username)
        return user.isDiskInList(diskTitle)
    }

    override fun getDatavisitProfile(username: String): List<Review> {
        val user = this.getByUsername(username)
        return this.reviewRepository.findAll()
                                    .stream()
                                    .filter { aReview -> aReview.userCreate.equals(user.username) }
                                    .collect(Collectors.toList())
    }

    override fun userFollowUSer(firstUser: String, secondUser: String): Boolean {
        val userSecond = this.getByUsername(secondUser)
        return userSecond.isFollowedBy(firstUser)
    }

    override fun follow(userToFollow: String, userFollower: String) {
        val user = this.getByUsername(userToFollow)
        val follower = this.getByUsername(userFollower)
        follower.follow(user)

        this.userRepository.save(follower)
        this.userRepository.save(user)
    }

    override fun unfollow(userFollower: String, userToUnfollow: String) {
        val user = this.getByUsername(userToUnfollow)
        val followerOfUser = this.getByUsername(userFollower)
        followerOfUser.unfollow(user)

        this.userRepository.save(user)
        this.userRepository.save(followerOfUser)
    }

    override fun seguidoresDe(username: String): List<Seguidores> {
        val user = this.getByUsername(username)
        return user.followers.stream()
                             .map { aUserFollower -> Seguidores(aUserFollower.username) }
                             .collect(Collectors.toList())
    }

    override fun seguidosDe(username: String): List<Seguidores> {
        val user = this.getByUsername(username)
        return user.following.stream()
                .map { aUserFollower -> Seguidores(aUserFollower.username) }
                .collect(Collectors.toList())
    }

}