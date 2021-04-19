package com.example.rosebud.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
    class User(@Id val username: String,
                   val password: String) {
}