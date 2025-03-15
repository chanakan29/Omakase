package com.example.omakase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_item")
data class MenuItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "course_type")
    val courseType: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "details")
    val details: String? = null // รายละเอียดอาจไม่มีก็ได้ เลยให้เป็น Nullable
)