package com.example.rosebud.service

import com.example.rosebud.model.User
import com.example.rosebud.model.exception.NoUserException
import com.example.rosebud.repository.ReviewRepository
import com.example.rosebud.repository.UserRepository
import com.example.rosebud.service.implementations.UserServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private var userRepository = Mockito.mock(UserRepository::class.java)
    private var reviewRepository = Mockito.mock(ReviewRepository::class.java)
    private var userSerive = UserServiceImpl(userRepository, reviewRepository)
    private var user = User("USER_TEST", "1234","TEST@gmail.com")

    private var userFollower = User("USER_FOLLOWER", "1234","TEST@gmail.com")
    private  var userToFollow = User("USER_TO_FOLLOW", "1234","TEST@gmail.com")

    @BeforeAll
    fun setUp() {
        Mockito.`when`(userRepository.save(userFollower)).thenReturn(userFollower)
        Mockito.`when`(userRepository.save(userToFollow)).thenReturn(userToFollow)


        Mockito.`when`(userRepository.save(user)).thenReturn(user)
        Mockito.`when`(userRepository.findAll()).thenReturn(listOf(user))
        Mockito.`when`(userRepository.findById("USER_TEST")).thenReturn(Optional.of(user))
        Mockito.`when`(userRepository.findById("NOT_USER")).thenReturn(Optional.empty())

        Mockito.`when`(userRepository.findById("USER_FOLLOWER")).thenReturn(Optional.of(userFollower))
        Mockito.`when`(userRepository.findById("USER_TO_FOLLOW")).thenReturn(Optional.of(userToFollow))

    }

    @Test
    fun test001_() {
        this.userSerive.save(user)
        val savedUsersFromDB = this.userSerive.getAllUsers()!!

        Assertions.assertEquals(1, savedUsersFromDB.size)
    }

    @Test
    fun test002_() {
        val stats = this.userSerive.userInfoProfile("USER_TEST")

        Assertions.assertEquals(0, stats.followers)
        Assertions.assertEquals(0, stats.following)
        Assertions.assertEquals(0, stats.moviesWatched)
    }

    @Test
    fun test003_() {
        Assertions.assertThrows(NoUserException::class.java) { this.userSerive.userInfoProfile("NOT_USER") }
    }

    @Test
    fun test004_() {
        this.userSerive.follow(userToFollow.username, userFollower.username)

        Assertions.assertTrue(this.userSerive.userFollowUSer(userFollower.username, userToFollow.username))
        Assertions.assertEquals(1, this.userSerive.seguidoresDe(userToFollow.username).size)
        Assertions.assertEquals(1, this.userSerive.seguidosDe(userFollower.username).size)
    }

    @Test
    fun test005_() {
        this.userSerive.follow(userToFollow.username, userFollower.username)
        this.userSerive.unfollow(userFollower.username, userToFollow.username)

        Assertions.assertEquals(0, this.userSerive.seguidoresDe(userToFollow.username).size)
        Assertions.assertEquals(0, this.userSerive.seguidosDe(userFollower.username).size)
    }

    @Test
    fun test006_() {
        Assertions.assertFalse(this.userSerive.isMovieInList("NOT_EXISTING_MOVIE", user.username))
        Assertions.assertFalse(this.userSerive.isDiskInList("NOT_EXISTING_DISK", user.username))
    }

}