package com.example.rosebud.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
    class User(@Id val username: String,
               val password: String,
               @OneToMany
               val reviews: List<Review>) {
}