package com.example.omakase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.omakase.entity.Course
import com.example.omakase.repository.CourseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(private val courseRepository: CourseRepository) : ViewModel() {

    private val _allCourses = courseRepository.allCourses.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val allCourses: StateFlow<List<Course>> get() = _allCourses

    fun insertCourse(course: Course) {
        viewModelScope.launch {
            courseRepository.insert(course)
        }
    }

    fun updateCourse(course: Course) {
        viewModelScope.launch {
            courseRepository.update(course)
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            courseRepository.delete(course)
        }
    }

    fun getCourseById(id: Int): StateFlow<Course> = courseRepository.getCourseById(id).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Course(0, "", "", 0.0) // ค่าเริ่มต้นที่เหมาะสม
    )

    class CourseViewModelFactory(private val repository: CourseRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CourseViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}