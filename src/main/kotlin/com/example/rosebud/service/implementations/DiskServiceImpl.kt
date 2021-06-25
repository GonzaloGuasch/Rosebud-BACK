package com.example.rosebud.service.implementations

import com.example.rosebud.model.Disk
import com.example.rosebud.repository.DiskRepository
import com.example.rosebud.service.interfaces.IDiskService
import org.springframework.stereotype.Service

@Service
class DiskServiceImpl(private val diskRepository: DiskRepository): IDiskService {

    override fun getAllDisks(): List<Disk> = this.diskRepository.findAll()

}