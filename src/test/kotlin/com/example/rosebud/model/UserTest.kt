package com.example.rosebud.model

import com.example.rosebud.model.exception.NoFollowerException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class UserTest {
    private lateinit var userOne: User
    private lateinit var userTwo: User
    private lateinit var movieOne: Movie

    @BeforeEach
    fun setUp() {
        userOne = User("Usuario test", "1234", "prueba@test.com")
        userTwo = User("Usuario test 2", "1234", "prueba2@test.com")
        movieOne = Movie("James cameron", "", 1994,"Action", "The terminator", Duration(2, 30))
    }

    @Test
    fun test001_UnUsuarioNoTieneSeguidoresNiSeguidosCuandoEsCreado() {
        Assertions.assertEquals(0, userOne.followers.size)
        Assertions.assertEquals(0, userOne.following.size)
        Assertions.assertEquals(0, userOne.moviesWatched.size)
    }

    @Test
    fun test002_UnUsuarioSigueAOtroAumentandoLaListaDeSusSeguidosYLosSeguidoresDelOtro() {
        userOne.follow(userTwo)

        Assertions.assertEquals(1, userTwo.followers.size)
        Assertions.assertEquals(1, userOne.following.size)
    }

    @Test
    fun test003_SiElUsuarioNoSigueAlQueQuieraDarUnfollowSeLevantaUnaException() {
        Assertions.assertThrows(NoFollowerException::class.java) { userOne.unfollow(userTwo) }
    }

    @Test
    fun test004_ElUsuarioSabeSiVioLaPeliculaPorSuTitulo() {
        userOne.addMovieWachted(movieOne)

        Assertions.assertTrue(userOne.isMovieInList("The terminator"))
    }

    @Test
    fun test005_SiElUsuarioVuelve() {
        userOne.addMovieWachted(movieOne)
        userOne.addMovieWachted(movieOne)

        Assertions.assertFalse(userOne.isMovieInList("The terminator"))
    }
}