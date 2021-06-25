package com.example.rosebud.service.interfaces

import com.example.rosebud.model.Disk

interface IDiskService {
    fun getAllDisks(): List<Disk>?
}

