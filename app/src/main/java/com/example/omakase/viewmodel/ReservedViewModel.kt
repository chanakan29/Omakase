package com.example.omakase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.omakase.entity.Reserved
import com.example.omakase.entity.Table
import com.example.omakase.repository.ReservedRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReservedViewModel(private val reservedRepository: ReservedRepository) : ViewModel() {

    private val _allReservations = reservedRepository.allReservations.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val allReservations: StateFlow<List<Reserved>> get() = _allReservations

    fun getReservationById(id: Int): StateFlow<Reserved> = reservedRepository.getReservationById(id).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Reserved(0, 0, 0, 0L, "") // แก้ไขตรงนี้: เปลี่ยน "" เป็น 0L
    )

    fun getReservationsByDate(date: Long): StateFlow<List<Reserved>> = reservedRepository.getReservationsByDate(date).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getReservationsByDateTime(date: Long, time: String): StateFlow<List<Reserved>> = reservedRepository.getReservationsByDateTime(date, time).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getReservationsByTableAndTime(tableId: Int, date: Long, time: String): StateFlow<Reserved?> = reservedRepository.getReservationsByTableAndTime(tableId, date, time).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun getAvailableTables(date: Long, time: String): StateFlow<List<Table>> = reservedRepository.getAvailableTables(date, time).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun reserveTable(reserved: Reserved) {
        viewModelScope.launch {
            reservedRepository.reserveTable(reserved)
        }
    }

    fun insert(reserved: Reserved) {
        viewModelScope.launch {
            reservedRepository.insert(reserved)
        }
    }

    fun update(reserved: Reserved) {
        viewModelScope.launch {
            reservedRepository.update(reserved)
        }
    }

    fun delete(reserved: Reserved) {
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