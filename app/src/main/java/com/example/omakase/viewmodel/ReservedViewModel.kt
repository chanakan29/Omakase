package com.example.omakase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.omakase.entity.Reserved
import com.example.omakase.entity.Table
import com.example.omakase.repository.ReservedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReservedViewModel(private val reservedRepository: ReservedRepository) : ViewModel() {

    val allReservations: Flow<List<Reserved>> = reservedRepository.allReservations

    fun getReservationById(id: Int) = reservedRepository.getReservationById(id)

    fun getReservationsByDate(date: Long) = reservedRepository.getReservationsByDate(date)

    fun getReservationsByDateTime(date: Long, time: String) = reservedRepository.getReservationsByDateTime(date, time)

    fun getReservationsByTableAndTime(tableId: Int, date: Long, time: String) = reservedRepository.getReservationsByTableAndTime(tableId, date, time)

    fun getAvailableTables(date: Long, time: String): Flow<List<Table>> = reservedRepository.getAvailableTables(date, time)

    suspend fun insert(reserved: Reserved) {
        viewModelScope.launch {
            reservedRepository.insert(reserved)
        }
    }

    suspend fun update(reserved: Reserved) {
        viewModelScope.launch {
            reservedRepository.update(reserved)
        }
    }

    suspend fun delete(reserved: Reserved) {
        viewModelScope.launch {
            reservedRepository.delete(reserved)
        }
    }

    class ReservedViewModelFactory(private val repository: ReservedRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ReservedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ReservedViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}