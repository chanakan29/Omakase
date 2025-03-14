package com.example.omakase.repository

import com.example.omakase.dao.MenuItemDao
import com.example.omakase.entity.MenuItem
import kotlinx.coroutines.flow.Flow

class MenuItemRepository(private val menuItemDao: MenuItemDao) {

    fun getMenuItemsByCourseId(courseId: Int): Flow<List<MenuItem>> =
        menuItemDao.getMenuItemsByCourseId(courseId)

    fun getMenuItemById(id: Int): Flow<MenuItem> = menuItemDao.getMenuItemById(id)

    suspend fun insert(menuItem: MenuItem) {
        menuItemDao.insert(menuItem)
    }

    suspend fun insertAll(menuItems: List<MenuItem>) {
        menuItemDao.insertAll(menuItems)
    }

    suspend fun update(menuItem: MenuItem) {
        menuItemDao.update(menuItem)
    }

    suspend fun delete(menuItem: MenuItem) {
        menuItemDao.delete(menuItem)
    }
}