package com.example.omakase.repository

import com.example.omakase.dao.CourseDao
import com.example.omakase.entity.Course
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val courseDao: CourseDao) {

    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()

    fun getCourseById(id: Int): Flow<Course> = courseDao.getCourseById(id)

    suspend fun insert(course: Course) {
        try {
            courseDao.insert(course)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
            // อาจจะ throw e ต่อ หรือ return Result.failure(e)
        }
    }

    suspend fun update(course: Course) {
        try {
            courseDao.update(course)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
            // อาจจะ throw e ต่อ หรือ return Result.failure(e)
        }
    }

    suspend fun delete(course: Course) {
        try {
            courseDao.delete(course)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
            // อาจจะ throw e ต่อ หรือ return Result.failure(e)
        }
    }
}