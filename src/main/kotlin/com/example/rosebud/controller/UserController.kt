package com.example.rosebud.controller
import com.example.rosebud.model.User
import com.example.rosebud.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @GetMapping("/")
    fun getAllMovies(): List<User>? = this.userService.getAllUsers()

    @PostMapping("/create")
    fun createUser(@RequestBody user: User): User = this.userService.save(user)

    @GetMapping("/info/{username}")
    fun userInfo(@PathVariable username: String) = this.userService.userInfoProfile(username)

    @GetMapping("/getDataVisitProfile/{username}")
    fun getDatavisitProfile(@PathVariable username: String) = this.userService.getDatavisitProfile(username)

    @GetMapping("/isMovieInList/{movieTitle}/{username}")
    fun isMovieInList(@PathVariable movieTitle: String, @PathVariable username: String) = this.userService.isMovieInList(movieTitle, username)

    @GetMapping("/sigueA/{firstUser}/{secondUser}")
    fun userFollowUser(@PathVariable firstUser: String, @PathVariable secondUser: String) = this.userService.userFollowUSer(firstUser, secondUser)


    @GetMapping("/seguidores/{username}")
    fun seguidoresDe(@PathVariable username: String) = this.userService.seguidoresDe(username)

    @GetMapping("/seguidos/{username}")
    fun seguidosDe(@PathVariable username: String) = this.userService.seguidosDe(username)

    @PostMapping("seguirA/{userToFollo}/{userFollower}")
    fun follow(@PathVariable userToFollo: String, @PathVariable userFollower: String) = this.userService.follow(userToFollo, userFollower)

    @PostMapping("dejarDeseguirA/{userFollower}/{userToUnfollow}")
    fun unfollow(@PathVariable userToUnfollow: String, @PathVariable userFollower: String) = this.userService.unfollow(userFollower, userToUnfollow)
}