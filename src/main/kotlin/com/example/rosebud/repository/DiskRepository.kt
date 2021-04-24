package com.example.rosebud.repository

import com.example.rosebud.model.Disk
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DiskRepository: JpaRepository<Disk, String> {
}