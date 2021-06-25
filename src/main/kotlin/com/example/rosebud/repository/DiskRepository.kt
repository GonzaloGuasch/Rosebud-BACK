package com.example.rosebud.repository

import com.example.rosebud.model.Disk
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface DiskRepository: JpaRepository<Disk, String> {

     fun findByTitleIgnoreCaseContaining(title: String): List<Disk>?
}