package com.example.rosebud.service.interfaces

import com.example.rosebud.model.Duration

interface IDurationService {

    fun save(duration: Duration): Duration
}