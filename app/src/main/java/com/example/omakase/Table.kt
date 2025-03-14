package com.example.omakase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tables")
data class Table(
    @PrimaryKey(autoGenerate = true)
    val tableId: Int = 0,
    val capacity: Int
)