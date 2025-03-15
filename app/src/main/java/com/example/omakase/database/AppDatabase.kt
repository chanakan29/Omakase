package com.example.omakase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.omakase.dao.MenuItemDao
import com.example.omakase.entity.MenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [MenuItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun menuItemDao(): MenuItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "omakase_database"
                )
                    .addCallback(AppDatabaseCallback(scope)) // เพิ่ม Callback ที่นี่
                    // .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() { // แก้ไขตรงนี้

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch(Dispatchers.IO) {
                    populateDatabase(it.menuItemDao())
                }
            }
        }

        suspend fun populateDatabase(menuItemDao: MenuItemDao) {
            // ลบข้อมูลเก่าออกก่อน (ถ้าต้องการ)
            // menuItemDao.deleteAll()

            // เพิ่มข้อมูลเมนูสำหรับคอร์สธรรมดา
            var menuItem = MenuItem(courseType = "regular", name = "ซูชิรวม", details = "ปลาแซลมอน, ปลาทูน่า, ปลากะพง")
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "regular", name = "สลัด", details = "ผักสด, น้ำสลัดญี่ปุ่น")
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "regular", name = "ไข่ตุ๋นทรงเครื่อง", details = "เนื้อไก่, เห็ดหอม, แครอท")
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "regular", name = "แคลิฟอร์เนียโรล", details = "ปูอัด, อะโวคาโด, ไข่กุ้ง")
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "regular", name = "ปลาหมึกย่างซีอิ๊ว", details = "เมนูพิเศษประจำฤดูกาล") // เพิ่มข้อความที่ชื่อเมนู
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "regular", name = "ซุปมิโซะ")
            menuItemDao.insertMenuItems(listOf(menuItem))

            // เพิ่มข้อมูลเมนูสำหรับคอร์สพรีเมี่ยม
            menuItem = MenuItem(courseType = "premium", name = "ซาชิมิรวม", details = "ปลาโอโทโร่, ปลาชูโทโร่, หอยเชลล์")
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "premium", name = "เทมปุระรวม", details = "กุ้งลายเสือ, ปลาไหล, ผักตามฤดูกาล")
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "premium", name = "ข้าวหน้าปลาไหลญี่ปุ่น", details = "ปลาไหลย่างซอสคาบายากิ")
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "premium", name = "ของหวานพิเศษ", details = "ไอศกรีมชาเขียวถั่วแดง")
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "premium", name = "ซุปทรัฟเฟิล", details = "ซุปใสรสเห็ดทรัฟเฟิล")
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "premium", name = "หอยเป๋าฮื้อนึ่งซีอิ๊ว", details = "เสิร์ฟพร้อมซอสสูตรพิเศษ (เมนูพิเศษ)") // เพิ่มข้อความที่รายละเอียดเมนู
            menuItemDao.insertMenuItems(listOf(menuItem))
            menuItem = MenuItem(courseType = "premium", name = "เนื้อวากิวย่าง", details = "A5 วากิวย่างบนเตาถ่าน")
            menuItemDao.insertMenuItems(listOf(menuItem))
        }
    }
}