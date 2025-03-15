package com.example.omakase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.omakase.R

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewSelectDateTime = view.findViewById(R.id.textViewSelectDateTime)
        datePicker = view.findViewById(R.id.datePicker)
        timeSpinner = view.findViewById(R.id.timeSpinner)
        buttonNext = view.findViewById(R.id.buttonNext)

        // กำหนดรายการรอบเวลาให้กับ Spinner
        val timeSlots = arrayOf("12:00", "14:00", "16:00", "18:00")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeSlots)
        timeSpinner.adapter = adapter

        // ... ส่วนของการทำงานของปุ่ม Next และการรับข้อมูลวันที่และเวลา ...
    }
}