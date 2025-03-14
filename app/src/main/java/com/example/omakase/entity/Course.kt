package com.example.omakase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val courseId: Int = 0,
    val name: String,
    val description: String,
    val price: Double
)