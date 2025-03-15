package com.example.omakase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.omakase.entity.MenuItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu_item WHERE course_type = :courseType")
    fun getMenuItemsByCourseType(courseType: String): Flow<List<MenuItem>>

    @Insert
    suspend fun insertMenuItems(menuItems: List<MenuItem>)

}