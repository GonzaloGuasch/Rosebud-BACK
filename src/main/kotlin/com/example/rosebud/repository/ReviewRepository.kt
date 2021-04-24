package com.example.rosebud.repository

import com.example.rosebud.model.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Int> {
}