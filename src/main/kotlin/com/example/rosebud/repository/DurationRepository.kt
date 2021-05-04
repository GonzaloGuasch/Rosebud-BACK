package com.example.rosebud.repository

import com.example.rosebud.model.Duration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DurationRepository: JpaRepository<Duration, Int> {

}