package com.example.rosebud.model.Offers

import javax.persistence.Entity

@Entity
class JobOffer(userAuthor: String,
               description: String,
               title: String,
               val remuneration: String,
               val location: String,
               val durationInWeeks: Int,
               val category: String) : Offer(userAuthor, description, title) {
}