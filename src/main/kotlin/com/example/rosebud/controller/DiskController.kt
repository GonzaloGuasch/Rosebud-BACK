package com.example.rosebud.controller

import com.example.rosebud.model.Disk
import com.example.rosebud.model.Element
import com.example.rosebud.model.wrapper.ElementRateWrapper
import com.example.rosebud.model.wrapper.ReviewWrapper
import com.example.rosebud.model.wrapper.WachtedListWrapper
import com.example.rosebud.service.ElementService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/disk")
class DiskController(private val elementService: ElementService){

    @PostMapping("/create")
    fun createDisk(@RequestBody diskToSave: Disk): Disk =  this.elementService.saveDiskWithoutPicture(diskToSave)

    @PostMapping("/addImage/{diskTitle}")
    fun addImageToDisk(@RequestParam("diskImage") diskImage: MultipartFile,
                       @PathVariable diskTitle: String) = this.elementService.addImageToDisk(diskImage, diskTitle)

    @GetMapping("/disk")
    fun getAllDisks(): List<Disk>? = this.elementService.getAllDisks()

    @GetMapping("/searchByTitle/{diskTitle}")
    fun getDiskByTitle(@PathVariable diskTitle: String): List<Element>? = this.elementService.getElementsMatchWithTitle(diskTitle, true)

    @GetMapping("/getByTitle/{diskTitle}")
    fun getByTitle(@PathVariable diskTitle: String): Element? = this.elementService.getElementByTitle(diskTitle, true)

    @PostMapping("/rate")
    fun rateDisk(@RequestBody elementRateWrapper: ElementRateWrapper): Element = this.elementService.rateElement(elementRateWrapper, true)

    @PostMapping("/leaveReview")
    fun leaveReview(@RequestBody reviewWrapper: ReviewWrapper): Element = this.elementService.leaveReviewInElement(reviewWrapper, true)

    //TODO terminarlos
    @PostMapping("/addToWachtedList")
    fun addToWachtedList(@RequestBody wachtedListWrapper: WachtedListWrapper): Boolean = this.elementService.addMovieToWatchedList(wachtedListWrapper)

    @GetMapping("statsForUser/{username}")
    fun getStatsForUser(@PathVariable username: String) = this.elementService.movieStatsForUser(username)
}