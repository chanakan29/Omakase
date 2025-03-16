package com.example.omakase.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.omakase.R
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import java.util.Locale


class PaymentFragment : Fragment() {

    private lateinit var textViewReservationSummary: TextView
    private lateinit var buttonConfirmPayment: Button
    private lateinit var textViewTotalAmount: TextView

    // กำหนดราคาเต็มของคอร์ส (บาท)
    private val courseFullPrices = mapOf(
        "คอร์สธรรมดา" to 2500,
        "คอร์สพรีเมี่ยม" to 4000
    )

    // กำหนดอัตรามัดจำ
    private val depositRate = 0.5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewReservationSummary = view.findViewById(R.id.textViewReservationSummary)
        buttonConfirmPayment = view.findViewById(R.id.buttonConfirmPayment)
        textViewTotalAmount = view.findViewById(R.id.textViewTotalAmount)

        // รับข้อมูลที่ส่งมาจาก UserInfoFragment
        val selectedDate = arguments?.getString("selectedDate")
        val selectedTime = arguments?.getString("selectedTime")
        val selectedCourse = arguments?.getString("selectedCourse")
        Log.d("PaymentFragment", "Selected Course: $selectedCourse")
        val firstName = arguments?.getString("firstName")
        val lastName = arguments?.getString("lastName")
        val email = arguments?.getString("email")
        val phone = arguments?.getString("phone")
        val numberOfPeople = arguments?.getInt("numberOfPeople")
        val additionalRequest = arguments?.getString("additionalRequest")

        // แสดงข้อมูลสรุปการจอง
        val reservationSummaryText = """
            สรุปการจอง:
            วันที่: $selectedDate
            เวลา: $selectedTime
            คอร์ส: $selectedCourse
            จำนวนคน: $numberOfPeople
            ชื่อ: $firstName $lastName
            อีเมล: $email
            เบอร์โทรศัพท์: $phone
            คำขอพิเศษ: $additionalRequest
        """.trimIndent()
        textViewReservationSummary.text = reservationSummaryText

        // คำนวณราคามัดจำ
        val fullPrice = courseFullPrices[selectedCourse] ?: 0
        val depositAmount = (fullPrice * depositRate).toInt()

        textViewTotalAmount.text = String.format(Locale.getDefault(), "%,d บาท (มัดจำ %.0f%%)", depositAmount, depositRate * 100)

        buttonConfirmPayment.setOnClickListener {
            // แสดงข้อความว่ากำลังดำเนินการชำระเงิน
            Toast.makeText(requireContext(), "กำลังดำเนินการชำระเงิน...", Toast.LENGTH_SHORT).show()

            // หน่วงเวลา 2 วินาที (จำลองการประมวลผล)
            Handler(Looper.getMainLooper()).postDelayed({
                // แสดงข้อความว่าชำระเงินสำเร็จ
                Toast.makeText(requireContext(), "ชำระเงินสำเร็จ!", Toast.LENGTH_SHORT).show()

                // TODO: นำทางไปยังหน้ายืนยันการจอง (ถ้าต้องการ)
                val confirmationFragment = ReservationConfirmationFragment()
                val bundle = Bundle()
                confirmationFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, confirmationFragment)
                    .addToBackStack(null)
                    .commit()

            }, 3000) // 1000 มิลลิวินาที = 1 วินาที
        }
    }
}