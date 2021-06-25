package com.example.rosebud.service.implementations

import com.example.rosebud.model.Duration
import com.example.rosebud.repository.DurationRepository
import com.example.rosebud.service.interfaces.IDurationService
import org.springframework.stereotype.Service

@Service
class DurationServiceImpl(private val durationRepository: DurationRepository): IDurationService {

    override fun save(duration: Duration): Duration = this.durationRepository.save(duration)

}