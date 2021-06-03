package com.example.rosebud.model

import org.hibernate.annotations.Type
import javax.persistence.*

@MappedSuperclass
open class Element(@Id
                   val title: String,
                   @OneToOne
                   val duration: Duration,
                   val gender: String,
                   var raiting: Int = 0,
                   var imagen: ByteArray = byteArrayOf(),
                   private var totalRaiting: Int = 0,
                   private var timesRated: Int = 0,
                   @OneToMany(cascade=[CascadeType.ALL])
                   var reviews: MutableSet<Review> = mutableSetOf()) {

    fun rate(userRate: Int) {
        this.timesRated += 1
        this.totalRaiting = this.totalRaiting + userRate
        val newRaiting = this.totalRaiting / this.timesRated
        this.raiting = minOf(5, newRaiting)
    }

    fun addReview(review: Review) = this.reviews.add(review)
}