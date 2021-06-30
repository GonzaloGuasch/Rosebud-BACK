package com.example.rosebud.repository

import com.example.rosebud.model.Disk
import com.example.rosebud.utils.QueryConstants
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface DiskRepository: JpaRepository<Disk, String> {

     fun findByTitleIgnoreCaseContaining(title: String): List<Disk>?

    @Query(value="SELECT duration.minutes " +
            QueryConstants.QUERY_DISK +
            "GROUP BY duration.minutes", nativeQuery = true)
     fun getMinutesListen(username: String): List<Int>

    @Query(value="SELECT duration.hours " +
            QueryConstants.QUERY_DISK, nativeQuery = true)
    fun getHoursListen(username: String): List<Int>

    @Query(value="SELECT disk.gender " +
            QueryConstants.QUERY_DISK  +
            "GROUP BY disk.gender " +
            "ORDER BY COUNT(disk.gender) " +
            "LIMIT 3", nativeQuery = true)
     fun getGendersOfUser(username: String): List<String>

    @Query(value="SELECT disk.band, COUNT(disk.band)" +
            QueryConstants.QUERY_DISK +
            "GROUP BY disk.band " +
            "ORDER BY SUM(duration.hours) DESC", nativeQuery = true)
     fun getStatsForUser(username: String): List<Any>
}