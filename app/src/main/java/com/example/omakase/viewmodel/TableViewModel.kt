package com.example.omakase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.omakase.entity.Table
import com.example.omakase.repository.TableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TableViewModel(private val tableRepository: TableRepository) : ViewModel() {

    val allTables: Flow<List<Table>> = tableRepository.allTables

    fun getTableById(id: Int) = tableRepository.getTableById(id)

    suspend fun insert(table: Table) {
        viewModelScope.launch {
            tableRepository.insert(table)
        }
    }

    suspend fun update(table: Table) {
        viewModelScope.launch {
            tableRepository.update(table)
        }
    }

    suspend fun delete(table: Table) {
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