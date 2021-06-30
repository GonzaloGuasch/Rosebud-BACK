package com.example.rosebud.controller

import com.example.rosebud.model.Disk
import com.example.rosebud.model.Element
import com.example.rosebud.model.wrapper.ElementRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.example.rosebud.service.implementations.ElementServiceImpl
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/disk")
class DiskController(private val elementServiceImpl: ElementServiceImpl){

    @PostMapping("/create")
    fun createDisk(@RequestBody diskToSave: Disk): Disk =  this.elementServiceImpl.saveDiskWithoutPicture(diskToSave)

    @PostMapping("/addImage/{diskTitle}")
    fun addImageToDisk(@RequestParam("diskImage") diskImage: MultipartFile,
                       @PathVariable diskTitle: String) = this.elementServiceImpl.addImageToDisk(diskImage, diskTitle)

    @GetMapping("/disk")
    fun getAllDisks(): List<Disk>? = this.elementServiceImpl.getAllDisks()

    @GetMapping("/searchByTitle/{diskTitle}")
    fun getDiskByTitle(@PathVariable diskTitle: String): List<Element>? = this.elementServiceImpl.getElementsMatchWithTitle(diskTitle, true)

    @GetMapping("/getByTitle/{diskTitle}")
    fun getByTitle(@PathVariable diskTitle: String): Element? = this.elementServiceImpl.getElementByTitle(diskTitle, true)

    @PostMapping("/rate")
    fun rateDisk(@RequestBody elementRateWrapper: ElementRateWrapper): Element = this.elementServiceImpl.rateElement(elementRateWrapper, true)

    @PostMapping("/leaveReview")
    fun leaveReview(@RequestBody reviewWrapper: ReviewWrapper): Element = this.elementServiceImpl.leaveReviewInElement(reviewWrapper, true)

    @PostMapping("/addToWachtedList")
    fun addToWachtedList(@RequestBody wachtedListWrapper: WachtedListWrapper): Boolean = this.elementServiceImpl.addElementToWatchedList(wachtedListWrapper, true)

    //TODO terminarlo
    @GetMapping("statsForUser/{username}")
    fun getStatsForUser(@PathVariable username: String) = this.elementServiceImpl.diskStatsForUser(username)
}