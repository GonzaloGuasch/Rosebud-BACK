package com.example.rosebud.service

import com.example.rosebud.model.Duration
import com.example.rosebud.repository.DurationRepository
import org.springframework.stereotype.Service

@Service
class DurationService(private val durationRepository: DurationRepository) {

    fun save(duration: Duration) = this.durationRepository.save(duration)
}