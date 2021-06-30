package com.example.rosebud.service.implementations

import com.example.rosebud.model.Disk
import com.example.rosebud.model.Element
import com.example.rosebud.model.Movie
import com.example.rosebud.model.Review
import com.example.rosebud.model.exception.NoElementException
import com.example.rosebud.model.exception.NoReviewException
import com.example.rosebud.repository.ReviewRepository
import com.example.rosebud.service.interfaces.IReviewService
import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl(private val reviewRepository: ReviewRepository,
                        private val elementServiceImpl: ElementServiceImpl): IReviewService {

    override fun deleteReview(reviewId: String, movieTitle: String) {
        val review: Review = getReviewOrError(reviewId)
        val element: Element = getElementOrError(movieTitle)
        element.removeReview(review)
        this.reviewRepository.delete(review)
        if(element.isMovie()) {
            this.elementServiceImpl.saveMovieWithoutPicture(element as Movie)
        }else{
            this.elementServiceImpl.saveDiskWithoutPicture(element as Disk)
        }
    }

    private fun getReviewOrError(reviewId: String): Review {
        val optinalReview = this.reviewRepository.findById(reviewId.toInt())
        if(optinalReview.isEmpty) {
            throw NoReviewException("No hay una review con ese id")
        }
        return optinalReview.get()
    }


    private fun getElementOrError(movieTitle: String): Element {
        val optinalElement = this.elementServiceImpl.getElementByTitle(movieTitle)
        if(optinalElement === null) {
            throw NoElementException("No hay un elemento con ese titulo")
        }
        return optinalElement
    }

}