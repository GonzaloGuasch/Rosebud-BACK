package com.example.rosebud.model
import java.lang.RuntimeException
import javax.persistence.*

@MappedSuperclass
abstract class Element(@Id
                       open val title: String,
                       open val year: Int,
                       open val description: String,
                       @OneToOne
                       open val duration: Duration,
                       open val gender: String,
                       open var raiting: Int = 0,
                       open var imagenPath: String = "",
                       private var totalRaiting: Int = 0,
                       private var timesRated: Int = 0,
                       @OneToMany(cascade=[CascadeType.ALL])
                       open var reviews: MutableSet<Review> = mutableSetOf()) {

    fun rate(userRate: Int) {
        this.timesRated += 1
        this.totalRaiting = this.totalRaiting + userRate
        val newRaiting = this.totalRaiting / this.timesRated
        this.raiting = minOf(5, newRaiting)
    }

    open fun addReview(review: Review) = this.reviews.add(review)
    fun removeReview(review: Review) {
        if(this.reviews.contains(review)) {
            this.reviews.remove(review)
            return
        }
        throw RuntimeException("There is no review for this!")
    }


    abstract fun isMovie(): Boolean
}