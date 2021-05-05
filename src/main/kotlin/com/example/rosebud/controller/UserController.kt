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
    fun userInfo(@PathVariable username: String) = this.userService.userInfo(username)

    @GetMapping("/isMovieInList/{movieTitle}/{username}")
    fun isMovieInList(@PathVariable movieTitle: String, @PathVariable username: String) = this.userService.isMovieInList(movieTitle, username)
}