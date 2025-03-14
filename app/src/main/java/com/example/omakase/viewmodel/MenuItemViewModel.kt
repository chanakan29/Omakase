package com.example.omakase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.omakase.entity.MenuItem
import com.example.omakase.repository.MenuItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MenuItemViewModel(private val menuItemRepository: MenuItemRepository) : ViewModel() {

    fun getMenuItemsByCourseId(courseId: Int): StateFlow<List<MenuItem>> =
        menuItemRepository.getMenuItemsByCourseId(courseId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getMenuItemById(id: Int): StateFlow<MenuItem> = menuItemRepository.getMenuItemById(id).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MenuItem(0, 0, "", "") // ค่าเริ่มต้นที่เหมาะสม
    )

    fun insert(menuItem: MenuItem) {
        viewModelScope.launch {
            menuItemRepository.insert(menuItem)
        }
    }

    fun insertAll(menuItems: List<MenuItem>) {
        viewModelScope.launch {
            menuItemRepository.insertAll(menuItems)
        }
    }

    fun update(menuItem: MenuItem) {
        viewModelScope.launch {
            menuItemRepository.update(menuItem)
        }
    }

    fun delete(menuItem: MenuItem) {
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