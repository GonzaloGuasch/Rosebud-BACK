package com.example.rosebud.model
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Duration(open var hours: Int,
                    open var minutes: Int,
                    @Id
                    @GeneratedValue
                    open var id: Int = 0) {

    fun amountOfMinutes() = (this.hours * 60) + this.minutes
}