package com.example.omakase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.omakase.entity.Reserved
import com.example.omakase.entity.Table // อย่าลืม Import Table
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

    // Query สำหรับค้นหาโต๊ะที่ว่าง ณ วันที่และเวลาที่กำหนด (ใช้ NOT EXISTS)
    @Query("SELECT T.* FROM tables AS T WHERE NOT EXISTS (SELECT 1 FROM reservations AS R WHERE R.tableId = T.tableId AND R.reservationDate = :date AND R.reservationTime = :time)")
    fun getAvailableTables(date: Long, time: String): Flow<List<Table>>

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