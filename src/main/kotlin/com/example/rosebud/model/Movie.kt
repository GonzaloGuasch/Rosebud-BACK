package com.example.rosebud.model

import javax.persistence.Entity

@Entity(name="movie")
class Movie(val director: String,
            title: String,
            duration: Duration): Element(title, duration) {

}


