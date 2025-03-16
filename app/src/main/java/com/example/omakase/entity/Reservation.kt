package com.example.omakase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class Reservation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val timeSlot: String,
    val courseType: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val numberOfPeople: Int? = null,
    val additionalRequest: String? = null
)