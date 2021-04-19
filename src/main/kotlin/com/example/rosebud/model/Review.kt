package com.example.rosebud.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Review(@Id
             @GeneratedValue
             var id: Int) {

}