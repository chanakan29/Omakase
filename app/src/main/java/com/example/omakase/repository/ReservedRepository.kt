package com.example.omakase.repository

import androidx.room.Transaction
import com.example.omakase.dao.ReservedDao
import com.example.omakase.dao.TableDao // Import TableDao
import com.example.omakase.entity.Reserved
import com.example.omakase.entity.Table
import kotlinx.coroutines.flow.Flow

class ReservedRepository(private val reservedDao: ReservedDao, private val tableDao: TableDao) { // รับ TableDao

    val allReservations: Flow<List<Reserved>> = reservedDao.getAllReservations()

    fun getReservationById(id: Int): Flow<Reserved> = reservedDao.getReservationById(id)

    fun getReservationsByDate(date: Long): Flow<List<Reserved>> = reservedDao.getReservationsByDate(date)

    fun getReservationsByDateTime(date: Long, time: String): Flow<List<Reserved>> = reservedDao.getReservationsByDateTime(date, time)

    fun getReservationsByTableAndTime(tableId: Int, date: Long, time: String): Flow<Reserved?> = reservedDao.getReservationsByTableAndTime(tableId, date, time)

    fun getAvailableTables(date: Long, time: String): Flow<List<Table>> = reservedDao.getAvailableTables(date, time)

    @Transaction
    suspend fun reserveTable(reserved: Reserved) {
        try {
            reservedDao.insert(reserved)
            // สมมติว่าคุณมี Function ใน TableDao สำหรับอัปเดตสถานะโต๊ะ
            // tableDao.updateTableStatus(reserved.tableId, false)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
            throw e // Re-throw exception เพื่อให้ Transaction rollback ถ้าจำเป็น
        }
    }

    suspend fun insert(reserved: Reserved) {
        try {
            reservedDao.insert(reserved)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }

    suspend fun update(reserved: Reserved) {
        try {
            reservedDao.update(reserved)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }

    suspend fun delete(reserved: Reserved) {
        try {
            reservedDao.delete(reserved)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }
}