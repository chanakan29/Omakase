package com.example.omakase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.omakase.Table
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {

    @Insert
    suspend fun insert(table: Table)

    @Update
    suspend fun update(table: Table)

    @Delete
    suspend fun delete(table: Table)

    @Query("SELECT * FROM tables")
    fun getAllTables(): Flow<List<Table>>

    @Query("SELECT * FROM tables WHERE tableId = :id")
    fun getTableById(id: Int): Flow<Table>
}