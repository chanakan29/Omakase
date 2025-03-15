package com.example.omakase.repository

import com.example.omakase.dao.TableDao
import com.example.omakase.entity.Table
import kotlinx.coroutines.flow.Flow

class TableRepository(private val tableDao: TableDao) {

    val allTables: Flow<List<Table>> = tableDao.getAllTables()

    fun getTableById(id: Int): Flow<Table> = tableDao.getTableById(id)

    suspend fun insert(table: Table) {
        try {
            tableDao.insert(table)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }

    suspend fun update(table: Table) {
        try {
            tableDao.update(table)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }

    suspend fun delete(table: Table) {
        try {
            tableDao.delete(table)
        } catch (e: Exception) {
            e.printStackTrace() // Log ข้อผิดพลาด
        }
    }
}