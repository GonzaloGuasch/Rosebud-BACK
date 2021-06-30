package com.example.rosebud.model.Offers

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class Offer(val userAuthor: String,
                 val description: String,
                 val title: String,
                 @Id
                 @GeneratedValue
                 val id: Long =0)