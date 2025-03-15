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
import com.example.omakase.R
import java.util.Calendar

class ReservationDateTimeFragment : Fragment() {

    private lateinit var textViewSelectDateTime: TextView
    private lateinit var datePicker: DatePicker
    private lateinit var timeSpinner: Spinner
    private lateinit var buttonNext: Button

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

        // กำหนดวันที่ต่ำสุดให้ DatePicker
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 2) // เพิ่มไป 2 วัน
        datePicker.minDate = calendar.timeInMillis

        // กำหนดรายการรอบเวลาเริ่มต้นให้กับ Spinner
        val allTimeSlots = arrayOf("12:00-13:30", "14:00-15:30", "16:00-17:30", "18:00-19:30")

        // ฟังก์ชันสำหรับกรองรอบเวลา
        fun filterTimeSlots(year: Int, month: Int, day: Int) {
            val currentCalendar = Calendar.getInstance()
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, day)

            val futureTimeSlots = mutableListOf<String>()

            // ถ้าวันที่เลือกคือวันนี้ ให้กรองรอบเวลา
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

                    // แปลงเวลาปัจจุบันและเวลารอบเป็นนาทีเพื่อเปรียบเทียบง่ายขึ้น
                    val currentTimeInMinutes = nowHour * 60 + nowMinute
                    val slotTimeInMinutes = hour * 60 + minute

                    if (slotTimeInMinutes > currentTimeInMinutes) {
                        futureTimeSlots.add(timeSlot)
                    }
                }
            } else {
                // ถ้าวันที่เลือกไม่ใช่ปัจจุบัน แสดงรอบเวลาทั้งหมด
                futureTimeSlots.addAll(allTimeSlots)
            }

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, futureTimeSlots)
            timeSpinner.adapter = adapter
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

            // ... ส่วนของการทำงานของปุ่ม Next และการรับข้อมูลวันที่และเวลา ...
        }
    }
}