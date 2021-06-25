package com.example.rosebud.model

import javax.persistence.Entity

@Entity(name="disk")
class Disk(val band: String,
               title: String,
               year: Int,
               description: String,
               gender: String,
               duration: Duration): Element(title, year, description, duration, gender) {
}