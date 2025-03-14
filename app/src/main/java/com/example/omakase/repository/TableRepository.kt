package com.example.omakase.repository

import com.example.omakase.dao.TableDao
import com.example.omakase.entity.Table
import kotlinx.coroutines.flow.Flow

class TableRepository(private val tableDao: TableDao) {

    val allTables: Flow<List<Table>> = tableDao.getAllTables()

    fun getTableById(id: Int): Flow<Table> = tableDao.getTableById(id)

    suspend fun insert(table: Table) {
        tableDao.insert(table)
    }

    suspend fun update(table: Table) {
        tableDao.update(table)
    }

    suspend fun delete(table: Table) {
        tableDao.delete(table)
    }
}