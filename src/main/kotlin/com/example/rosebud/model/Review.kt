package com.example.rosebud.model

import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Review(@NotNull
             var userCreate: String,
             @NotNull
             var review: String,
             var hasSpoilers: Boolean,
             @Id
             @GeneratedValue
             var id: Int = 0,) {

}