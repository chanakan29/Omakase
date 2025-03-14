package com.example.omakase.repository

import com.example.omakase.dao.MenuItemDao
import com.example.omakase.entity.MenuItem
import kotlinx.coroutines.flow.Flow

class MenuItemRepository(private val menuItemDao: MenuItemDao) {

    fun getMenuItemsByCourseId(courseId: Int): Flow<List<MenuItem>> =
        menuItemDao.getMenuItemsByCourseId(courseId)

    fun getMenuItemById(id: Int): Flow<MenuItem> = menuItemDao.getMenuItemById(id)

    suspend fun insert(menuItem: MenuItem) {
        try {
            menuItemDao.insert(menuItem)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }

    suspend fun insertAll(menuItems: List<MenuItem>) {
        try {
            menuItemDao.insertAll(menuItems)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }

    suspend fun update(menuItem: MenuItem) {
        try {
            menuItemDao.update(menuItem)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }

    suspend fun delete(menuItem: MenuItem) {
        try {
            menuItemDao.delete(menuItem)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }
}