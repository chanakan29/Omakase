package com.example.omakase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CourseSelectionViewModel : ViewModel() {
    private val _selectedCourse = MutableLiveData<String?>()
    val selectedCourse: LiveData<String?> = _selectedCourse

    fun selectCourse(courseName: String) {
        _selectedCourse.value = courseName
    }

    fun clearSelectedCourse() {
        _selectedCourse.value = null
    }
}