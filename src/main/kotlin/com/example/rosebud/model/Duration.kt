package com.example.rosebud.model
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Duration(var hours: Int,
               var minutes: Int,
               @Id
               @GeneratedValue
               var id: Int = 0) {

    fun amountOfMinutes() = (this.hours * 60) + this.minutes
}