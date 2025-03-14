package com.example.omakase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.omakase.entity.MenuItem
import com.example.omakase.repository.MenuItemRepository
import kotlinx.coroutines.launch

class MenuItemViewModel(private val menuItemRepository: MenuItemRepository) : ViewModel() {

    fun getMenuItemsByCourseId(courseId: Int) = menuItemRepository.getMenuItemsByCourseId(courseId)

    fun getMenuItemById(id: Int) = menuItemRepository.getMenuItemById(id)

    suspend fun insert(menuItem: MenuItem) {
        viewModelScope.launch {
            menuItemRepository.insert(menuItem)
        }
    }

    suspend fun insertAll(menuItems: List<MenuItem>) {
        viewModelScope.launch {
            menuItemRepository.insertAll(menuItems)
        }
    }

    suspend fun update(menuItem: MenuItem) {
        viewModelScope.launch {
            menuItemRepository.update(menuItem)
        }
    }

    suspend fun delete(menuItem: MenuItem) {
        viewModelScope.launch {
            menuItemRepository.delete(menuItem)
        }
    }

    class MenuItemViewModelFactory(private val repository: MenuItemRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MenuItemViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}