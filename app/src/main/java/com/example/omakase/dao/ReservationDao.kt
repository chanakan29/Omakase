package com.example.omakase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.omakase.entity.Reservation
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Insert
    suspend fun insert(reservation: Reservation)

    @Query("SELECT * FROM reservations WHERE date = :date")
    suspend fun getReservationsByDate(date: String): List<Reservation>

    @Query("SELECT * FROM reservations WHERE firstName IS NOT NULL AND firstName != '' AND lastName IS NOT NULL AND lastName != ''")
    fun getAllReservations(): Flow<List<Reservation>>
}