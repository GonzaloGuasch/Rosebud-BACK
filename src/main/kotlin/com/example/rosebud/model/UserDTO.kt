package com.example.rosebud.model

class UserDTO(val username: String,
              val email: String,
              val diskListen: MutableSet<String>,
              val moviesWatched: MutableSet<String>) {
}