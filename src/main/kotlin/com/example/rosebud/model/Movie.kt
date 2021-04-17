package com.example.rosebud.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name="movie")
class Movie(@Id
            val title: String,
            val director: String) {
}