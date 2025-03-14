package com.example.omakase.repository

import com.example.omakase.dao.ReservedDao
import com.example.omakase.entity.Reserved
import com.example.omakase.entity.Table // Import Table Entity
import kotlinx.coroutines.flow.Flow

class ReservedRepository(private val reservedDao: ReservedDao) {

    val allReservations: Flow<List<Reserved>> = reservedDao.getAllReservations()

    fun getReservationById(id: Int): Flow<Reserved> = reservedDao.getReservationById(id)

    fun getReservationsByDate(date: Long): Flow<List<Reserved>> = reservedDao.getReservationsByDate(date)

    fun getReservationsByDateTime(date: Long, time: String): Flow<List<Reserved>> = reservedDao.getReservationsByDateTime(date, time)

    fun getReservationsByTableAndTime(tableId: Int, date: Long, time: String): Flow<Reserved?> = reservedDao.getReservationsByTableAndTime(tableId, date, time)

    fun getAvailableTables(date: Long, time: String): Flow<List<Table>> = reservedDao.getAvailableTables(date, time)

    suspend fun insert(reserved: Reserved) {
        reservedDao.insert(reserved)
    }

    suspend fun update(reserved: Reserved) {
        reservedDao.update(reserved)
    }

    suspend fun delete(reserved: Reserved) {
        reservedDao.delete(reserved)
    }
}