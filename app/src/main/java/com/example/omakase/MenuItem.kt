package com.example.omakase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "menu_items",
    foreignKeys = [ForeignKey(
        entity = Course::class,
        parentColumns = ["courseId"],
        childColumns = ["courseId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MenuItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val courseId: Int,
    val name: String,
    val ingredients: String
)