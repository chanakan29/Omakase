package com.example.omakase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.omakase.dao.ReservationDao
import com.example.omakase.entity.Reservation

class ReservationListViewModel(reservationDao: ReservationDao) : ViewModel() {
    val reservations: LiveData<List<Reservation>> = reservationDao.getAllReservations().asLiveData()
}

class ReservationListViewModelFactory(private val reservationDao: ReservationDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReservationListViewModel(reservationDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}