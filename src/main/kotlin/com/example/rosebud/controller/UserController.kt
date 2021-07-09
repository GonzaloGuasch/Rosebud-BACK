package com.example.rosebud.controller
import com.example.rosebud.model.User
import com.example.rosebud.model.UserDTO
import com.example.rosebud.model.wrapper.LoginWrapper
import com.example.rosebud.service.implementations.UserServiceImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userServiceImpl: UserServiceImpl) {

    @GetMapping("/")
    fun getAllMovies(): List<User>? = this.userServiceImpl.getAllUsers()

    @PostMapping("/create")
    fun createUser(@RequestBody user: User): User = this.userServiceImpl.save(user)

    @PostMapping("/login")
    fun login(@RequestBody user: LoginWrapper): UserDTO = this.userServiceImpl.login(user)

    @GetMapping("/info/{username}")
    fun userInfo(@PathVariable username: String) = this.userServiceImpl.userInfoProfile(username)

    @GetMapping("/getDataVisitProfile/{username}")
    fun getDatavisitProfile(@PathVariable username: String) = this.userServiceImpl.getDatavisitProfile(username)

    @GetMapping("/isMovieInList/{movieTitle}/{username}")
    fun isMovieInList(@PathVariable movieTitle: String, @PathVariable username: String) = this.userServiceImpl.isMovieInList(movieTitle, username)

    @GetMapping("/isDiskInList/{diskTitle}/{username}")
    fun isDiskInList(@PathVariable diskTitle: String, @PathVariable username: String) = this.userServiceImpl.isDiskInList(diskTitle, username)

    @GetMapping("/sigueA/{firstUser}/{secondUser}")
    fun userFollowUser(@PathVariable firstUser: String, @PathVariable secondUser: String) = this.userServiceImpl.userFollowUSer(firstUser, secondUser)

    @GetMapping("/seguidores/{username}")
    fun seguidoresDe(@PathVariable username: String) = this.userServiceImpl.seguidoresDe(username)

    @GetMapping("/seguidos/{username}")
    fun seguidosDe(@PathVariable username: String) = this.userServiceImpl.seguidosDe(username)

    @PostMapping("seguirA/{userToFollo}/{userFollower}")
    fun follow(@PathVariable userToFollo: String, @PathVariable userFollower: String) = this.userServiceImpl.follow(userToFollo, userFollower)

    @PostMapping("dejarDeseguirA/{userToUnfollow}/{userFollower}")
    fun unfollow(@PathVariable userToUnfollow: String, @PathVariable userFollower: String) = this.userServiceImpl.unfollow(userFollower, userToUnfollow)
}