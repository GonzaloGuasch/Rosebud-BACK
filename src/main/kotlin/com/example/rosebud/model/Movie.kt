package com.example.rosebud.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name="movie")
class Movie(@Id
            val title: String,
            val director: String,
            var raiting: Int = 0,
            private var totalRaiting: Int = 0,
            private var timesRated: Int = 0) {


    fun rate(userRate: Int) {
        this.timesRated += 1
        this.totalRaiting = this.totalRaiting + userRate
        val newRaiting = this.totalRaiting / this.timesRated
        this.raiting = minOf(5, newRaiting)
    }
}


