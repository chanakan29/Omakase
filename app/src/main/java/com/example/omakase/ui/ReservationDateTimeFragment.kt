package com.example.omakase.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.omakase.R
import com.example.omakase.database.AppDatabase
import com.example.omakase.dao.ReservationDao
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class ReservationDateTimeFragment : Fragment() {

    private lateinit var textViewSelectDateTime: TextView
    private lateinit var datePicker: DatePicker
    private lateinit var timeSpinner: Spinner
    private lateinit var buttonNext: Button

    private var db: AppDatabase? = null
    private var reservationDao: ReservationDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_date_time, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewSelectDateTime = view.findViewById(R.id.textViewSelectDateTime)
        datePicker = view.findViewById(R.id.datePicker)
        timeSpinner = view.findViewById(R.id.timeSpinner)
        buttonNext = view.findViewById(R.id.buttonNext)

        // กำหนดวันที่ต่ำสุดให้ DatePicker (จองล่วงหน้าอย่างน้อย 2 วัน)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 2)
        datePicker.minDate = calendar.timeInMillis

        // กำหนดวันที่สูงสุดให้ DatePicker (3 เดือนนับจากวันที่ต่ำสุด)
        val calendarMaxDate = Calendar.getInstance()
        calendarMaxDate.timeInMillis = calendar.timeInMillis // เริ่มต้นจากวันที่ต่ำสุด
        calendarMaxDate.add(Calendar.MONTH, 3)
        datePicker.maxDate = calendarMaxDate.timeInMillis

        // กำหนดรายการรอบเวลาเริ่มต้นให้กับ Spinner
        val allTimeSlots = arrayOf("12:00-13:30", "14:00-15:30", "16:00-17:30", "18:00-19:30")

        // สร้าง Instance ของ Database และ DAO
        db = AppDatabase.getDatabase(requireContext().applicationContext, lifecycleScope)
        reservationDao = db?.reservationDao()

        // ฟังก์ชันสำหรับกรองรอบเวลาจาก Database
        fun filterTimeSlots(year: Int, month: Int, day: Int) {
            val currentCalendar = Calendar.getInstance()
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, day)

            val futureTimeSlots = mutableListOf<String>()
            val selectedDateString = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day)

            lifecycleScope.launch {
                val bookedReservations = reservationDao?.getReservationsByDate(selectedDateString) ?: emptyList()
                val bookedTimeSlots = bookedReservations.map { it.timeSlot }

                // ถ้าวันที่เลือกคือวันนี้ ให้กรองรอบเวลาปัจจุบัน
                if (selectedCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) &&
                    selectedCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) &&
                    selectedCalendar.get(Calendar.DAY_OF_MONTH) == currentCalendar.get(Calendar.DAY_OF_MONTH)) {

                    val nowHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
                    val nowMinute = currentCalendar.get(Calendar.MINUTE)

                    for (timeSlot in allTimeSlots) {
                        val startTime = timeSlot.split("-")[0]
                        val parts = startTime.split(":")
                        val hour = parts[0].toInt()
                        val minute = parts[1].toInt()

                        val currentTimeInMinutes = nowHour * 60 + nowMinute
                        val slotTimeInMinutes = hour * 60 + minute

                        if (slotTimeInMinutes > currentTimeInMinutes && !bookedTimeSlots.contains(timeSlot)) {
                            futureTimeSlots.add(timeSlot)
                        }
                    }
                } else {
                    // ถ้าวันที่เลือกไม่ใช่ปัจจุบัน ให้แสดงเฉพาะรอบเวลาที่ยังไม่ถูกจอง
                    for (timeSlot in allTimeSlots) {
                        if (!bookedTimeSlots.contains(timeSlot)) {
                            futureTimeSlots.add(timeSlot)
                        }
                    }
                }

                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, futureTimeSlots)
                timeSpinner.adapter = adapter
            }
        }

        // เรียกฟังก์ชัน filterTimeSlots ครั้งแรกเมื่อ Fragment ถูกสร้าง (สำหรับวันที่ปัจจุบัน)
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        filterTimeSlots(initialYear, initialMonth, initialDay)

        // ตั้งค่า Listener ให้กับ DatePicker เพื่อกรองรอบเวลาเมื่อวันที่เปลี่ยน
        datePicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
            filterTimeSlots(year, month, dayOfMonth)
        }

        buttonNext.setOnClickListener {
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            val time = timeSpinner.selectedItem.toString()
            val selectedCourse = arguments?.getString("courseType") ?: ""

            val selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day)

            // สร้าง Bundle เพื่อส่งข้อมูลไปยัง Fragment ถัดไป
            val bundle = Bundle().apply {
                putString("selectedDate", selectedDate)
                putString("selectedTime", time)
                putString("selectedCourse", selectedCourse)
            }

            // ตัวอย่างการนำทางไปยัง Fragment ถัดไป (สมมติว่าเป็น Fragment ชื่อ UserInfoFragment)
            val nextFragment = UserInfoFragment()
            nextFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, nextFragment) // แทนที่ R.id.fragment_container ด้วย ID ของ Container ที่ใช้แสดง Fragment
                .addToBackStack(null)
                .commit()
        }
        }
    }