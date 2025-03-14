package com.example.omakase.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.omakase.entity.*
import com.example.omakase.dao.*

@Database(
    entities = [Course::class, MenuItem::class, Table::class, Reserved::class],
    version = 1,
    exportSchema = false
)
abstract class OmakaseDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao
    abstract fun menuItemDao(): MenuItemDao
    abstract fun tableDao(): TableDao
    abstract fun reservedDao(): ReservedDao

    companion object {
        @Volatile
        private var INSTANCE: OmakaseDatabase? = null

        fun getDatabase(context: Context): OmakaseDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    OmakaseDatabase::class.java,
                    "omakase_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(databaseCallback)
                    .build()
                    .also { INSTANCE = it }
            }
        }

        private val databaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}
