package com.example.omakase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.omakase.Reserved
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservedDao {

    @Insert
    suspend fun insert(reserved: Reserved)

    @Update
    suspend fun update(reserved: Reserved)

    @Delete
    suspend fun delete(reserved: Reserved)

    @Query("SELECT * FROM reservations")
    fun getAllReservations(): Flow<List<Reserved>>

    @Query("SELECT * FROM reservations WHERE reservationId = :id")
    fun getReservationById(id: Int): Flow<Reserved>

    // Query สำหรับค้นหาโต๊ะที่ว่าง ณ วันที่และเวลาที่กำหนด
    @Query("SELECT T.* FROM tables AS T LEFT JOIN reservations AS R ON T.tableId = R.tableId WHERE R.reservationDate != :date OR R.reservationTime != :time OR R.tableId IS NULL")
    fun getAvailableTables(date: Long, time: String): Flow<List<com.example.omakase.Table>>

    // Query สำหรับค้นหาการจองตามวันที่
    @Query("SELECT * FROM reservations WHERE reservationDate = :date")
    fun getReservationsByDate(date: Long): Flow<List<Reserved>>

    // Query สำหรับค้นหาการจองตามวันที่และเวลา
    @Query("SELECT * FROM reservations WHERE reservationDate = :date AND reservationTime = :time")
    fun getReservationsByDateTime(date: Long, time: String): Flow<List<Reserved>>

    // Query สำหรับค้นหาการจองตามโต๊ะและช่วงเวลา
    @Query("SELECT * FROM reservations WHERE tableId = :tableId AND reservationDate = :date AND reservationTime = :time")
    fun getReservationsByTableAndTime(tableId: Int, date: Long, time: String): Flow<Reserved?>
}