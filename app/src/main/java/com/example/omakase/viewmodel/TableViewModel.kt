package com.example.omakase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.omakase.entity.Table
import com.example.omakase.repository.TableRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TableViewModel(private val tableRepository: TableRepository) : ViewModel() {

    private val _allTables = tableRepository.allTables.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val allTables: StateFlow<List<Table>> get() = _allTables

    fun getTableById(id: Int): StateFlow<Table> = tableRepository.getTableById(id).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Table(0, 0) // ค่าเริ่มต้นที่เหมาะสม
    )

    fun insert(table: Table) {
        viewModelScope.launch {
            tableRepository.insert(table)
        }
    }

    fun update(table: Table) {
        viewModelScope.launch {
            tableRepository.update(table)
        }
    }

    fun delete(table: Table) {
        viewModelScope.launch {
            tableRepository.delete(table)
        }
    }

    class TableViewModelFactory(private val repository: TableRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TableViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TableViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}