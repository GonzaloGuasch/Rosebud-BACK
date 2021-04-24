package com.example.rosebud.service

import com.example.rosebud.model.Disk
import com.example.rosebud.repository.DiskRepository
import org.springframework.stereotype.Service

@Service
class DiskService(private val diskRepository: DiskRepository) {

    fun getAllDisks(): List<Disk> = this.diskRepository.findAll()

}