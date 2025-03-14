package com.example.omakase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.omakase.entity.MenuItem
import kotlinx.coroutines.flow.Flow

@Dao
interface   MenuItemDao {

    @Insert
    suspend fun insert(menuItem: MenuItem)

    @Insert
    suspend fun insertAll(menuItems: List<MenuItem>) // เพิ่มฟังก์ชันสำหรับ Insert หลายรายการ

    @Update
    suspend fun update(menuItem: MenuItem)

    @Delete
    suspend fun delete(menuItem: MenuItem)

    @Query("SELECT * FROM menu_items WHERE courseId = :courseId")
    fun getMenuItemsByCourseId(courseId: Int): Flow<List<MenuItem>>

    @Query("SELECT * FROM menu_items WHERE itemId = :id")
    fun getMenuItemById(id: Int): Flow<MenuItem>
}