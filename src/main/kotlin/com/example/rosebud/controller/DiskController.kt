package com.example.rosebud.controller

import com.example.rosebud.service.DiskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/disk")
class DiskController(private val diskService: DiskService){

    @GetMapping("/")
    fun getAllDisks() = this.diskService.getAllDisks()
}