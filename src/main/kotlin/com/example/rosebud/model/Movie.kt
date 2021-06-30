package com.example.rosebud.model

import javax.persistence.Entity

@Entity(name="movie")
open class Movie(open val director: String,
                 description: String,
                 year: Int,
                 gender: String,
                 title: String,
                 duration: Duration): Element(title, year, description, duration, gender) {

    override fun isMovie() = true
}


