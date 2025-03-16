package com.example.omakase.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.omakase.R
import android.widget.TextView
import android.widget.Button
import com.example.omakase.entity.Reservation
import com.example.omakase.dao.ReservationDao
import com.example.omakase.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class ReservationConfirmationFragment : Fragment() {

    private lateinit var textViewConfirmationMessage: TextView
    private lateinit var buttonGoHome: Button
    private lateinit var reservationDao: ReservationDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewConfirmationMessage = view.findViewById(R.id.textViewConfirmationMessage)
        textViewConfirmationMessage.text = "การจองของคุณได้รับการยืนยันแล้ว!"

        buttonGoHome = view.findViewById(R.id.buttonGoHome)
        buttonGoHome.setOnClickListener {
            val homeFragment = HomeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, homeFragment)
                .commit()
        }

        // เข้าถึง Database โดยส่ง lifecycleScope ไปด้วย
        val db = AppDatabase.getDatabase(requireContext(), lifecycleScope)
        reservationDao = db.reservationDao()

        // ดึงข้อมูลการจองจาก arguments โดยใช้ชื่อ Field ใน Entity เป็น Key
        val date = arguments?.getString("date")
        val timeSlot = arguments?.getString("timeSlot")
        val courseType = arguments?.getString("courseType")
        val firstName = arguments?.getString("firstName")
        val lastName = arguments?.getString("lastName")
        val email = arguments?.getString("email")
        val phone = arguments?.getString("phone")
        val numberOfPeople = arguments?.getInt("numberOfPeople")
        val additionalRequest = arguments?.getString("additionalRequest")

        Log.d("ConfirmationFragment", "Date: $date")
        Log.d("ConfirmationFragment", "Time Slot: $timeSlot")
        Log.d("ConfirmationFragment", "Course Type: $courseType")
        Log.d("ConfirmationFragment", "First Name: $firstName")
        Log.d("ConfirmationFragment", "Last Name: $lastName")
        Log.d("ConfirmationFragment", "Email: $email")
        Log.d("ConfirmationFragment", "Phone: $phone")
        Log.d("ConfirmationFragment", "Number of People: $numberOfPeople")
        Log.d("ConfirmationFragment", "Additional Request: $additionalRequest")

        val newReservation = Reservation(
            date = date ?: "",
            timeSlot = timeSlot ?: "",
            courseType = courseType ?: "",
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            numberOfPeople = numberOfPeople,
            additionalRequest = additionalRequest
        )

        CoroutineScope(Dispatchers.IO).launch {
            reservationDao.insert(newReservation)
        }

    }
}