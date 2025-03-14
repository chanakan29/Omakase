package com.example.omakase.repository

import com.example.omakase.dao.CourseDao
import com.example.omakase.entity.Course
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val courseDao: CourseDao) {

    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()

    suspend fun insert(course: Course) {
        courseDao.insert(course)
    }

    suspend fun update(course: Course) {
        courseDao.update(course)
    }

    suspend fun delete(course: Course) {
        courseDao.delete(course)
    }

    fun getCourseById(id: Int): Flow<Course> {
        return courseDao.getCourseById(id)
    }
}
