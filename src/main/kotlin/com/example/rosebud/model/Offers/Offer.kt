package com.example.rosebud.model.Offers

import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class Offer(@Id val userAuthor: String,
                 val description: String,
                 val title: String){
}