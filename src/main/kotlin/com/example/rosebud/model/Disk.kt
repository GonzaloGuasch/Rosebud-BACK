package com.example.rosebud.model

import javax.persistence.Entity

@Entity(name="disk")
class Disk(val band: String,
           title: String,
           duration: Duration): Element(title, duration) {
}