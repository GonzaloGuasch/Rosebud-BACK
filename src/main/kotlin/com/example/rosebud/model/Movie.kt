package com.example.rosebud.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Movie(@Id
            val title: String,
            val director: String) {
}