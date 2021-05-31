package com.example.rosebud.model.Offers

import javax.persistence.Entity

@Entity
class JobOffer(userAuthor: String,
               description: String,
               title: String,
               val remuneration: String,
               val location: String,
               val durationInWeeks: Int,
               val linkReference: String) : Offer(userAuthor, description, title) {
}