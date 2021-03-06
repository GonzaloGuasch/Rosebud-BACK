package com.example.rosebud.model

import javax.persistence.Entity

@Entity(name="disk")
open class Disk(open val band: String,
                title: String,
                year: Int,
                description: String,
                gender: String,
                duration: Duration): Element(title, year, description, duration, gender) {

    override fun isMovie() = false
}