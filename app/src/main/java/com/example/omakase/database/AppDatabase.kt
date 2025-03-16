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
import com.example.omakase.dao.ReservationDao
import com.example.omakase.entity.Reservation

@Database(entities = [MenuItem::class, Reservation::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun menuItemDao(): MenuItemDao
    abstract fun reservationDao(): ReservationDao

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
                    //.fallbackToDestructiveMigration()
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
                    populateDatabase(it.menuItemDao(), it.reservationDao())
                }
            }
        }

        suspend fun populateDatabase(menuItemDao: MenuItemDao, reservationDao: ReservationDao) {
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
            menuItem = MenuItem(courseType = "regular", name = "ซุปมิโซะ", details = "เต้าหู้, สาหร่าย")
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

            // คุณสามารถเพิ่มข้อมูลการจองจำลองได้ตามต้องการ
            val reservation1 = Reservation(date = "2025-03-18", timeSlot = "14:00-15:30", courseType = "regular")
            reservationDao.insert(reservation1)
            val reservation2 = Reservation(date = "2025-03-20", timeSlot = "12:00-13:30", courseType = "premium")
            reservationDao.insert(reservation2)
            val reservation3 = Reservation(date = "2025-03-20", timeSlot = "16:00-17:30", courseType = "regular")
            reservationDao.insert(reservation3)
            val reservation4 = Reservation(date = "2025-03-22", timeSlot = "18:00-19:30", courseType = "premium")
            reservationDao.insert(reservation4)

            // เพิ่มข้อมูลการจองสำหรับวันที่ 5 เมษายน
            var reservation = Reservation(date = "2025-04-05", timeSlot = "12:00-13:30", courseType = "premium")
            reservationDao.insert(reservation)
            reservation = Reservation(date = "2025-04-05", timeSlot = "16:00-17:30", courseType = "regular")
            reservationDao.insert(reservation)
            reservation = Reservation(date = "2025-04-05", timeSlot = "14:00-15:30", courseType = "premium")
            reservationDao.insert(reservation)
            reservation = Reservation(date = "2025-04-05", timeSlot = "18:00-19:30", courseType = "premium")
            reservationDao.insert(reservation)

            // เพิ่มข้อมูลการจองสำหรับวันที่ 6 เมษายน
            reservation = Reservation(date = "2025-04-06", timeSlot = "14:00-15:30", courseType = "regular")
            reservationDao.insert(reservation)
            reservation = Reservation(date = "2025-04-06", timeSlot = "18:00-19:30", courseType = "premium")
            reservationDao.insert(reservation)

            // เพิ่มข้อมูลการจองสำหรับวันที่ 7 เมษายน
            reservation = Reservation(date = "2025-04-07", timeSlot = "12:00-13:30", courseType = "regular")
            reservationDao.insert(reservation)
            reservation = Reservation(date = "2025-04-07", timeSlot = "16:00-17:30", courseType = "premium")
            reservationDao.insert(reservation)
            reservation = Reservation(date = "2025-04-07", timeSlot = "18:00-19:30", courseType = "regular")
            reservationDao.insert(reservation)

            // เพิ่มข้อมูลการจองสำหรับวันที่ 8 เมษายน
            reservation = Reservation(date = "2025-04-08", timeSlot = "14:00-15:30", courseType = "premium")
            reservationDao.insert(reservation)
            reservation = Reservation(date = "2025-04-08", timeSlot = "18:00-19:30", courseType = "regular")
            reservationDao.insert(reservation)
        }
    }
}