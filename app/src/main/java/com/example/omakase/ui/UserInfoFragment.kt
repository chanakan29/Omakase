package com.example.omakase.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.omakase.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class UserInfoFragment : Fragment() {

    private lateinit var textViewReservationDetails: TextView
    private lateinit var editTextFirstName: TextInputEditText
    private lateinit var editTextLastName: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPhone: TextInputEditText
    private lateinit var spinnerPeople: Spinner // เปลี่ยนจาก NumberPicker เป็น Spinner
    private lateinit var editTextAdditionalRequest: TextInputEditText
    private lateinit var buttonConfirmReservation: Button
    private lateinit var textInputLayoutFirstName: TextInputLayout
    private lateinit var textInputLayoutLastName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPhone: TextInputLayout
    private lateinit var textInputLayoutAdditionalRequest: TextInputLayout // เพิ่มตัวแปรนี้

    // Regular expression สำหรับตรวจสอบชื่อ (อนุญาตตัวอักษรและช่องว่าง)
    private val namePattern = Pattern.compile("^[a-zA-Z\\u0E00-\\u0E7F\\s]+$")

    // Regular expression สำหรับตรวจสอบเบอร์โทรศัพท์ (อนุญาตเฉพาะตัวเลข และอาจมีเครื่องหมาย + นำหน้า)
    private val phonePattern = Pattern.compile("^\\+?\\d{8,15}$")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewReservationDetails = view.findViewById(R.id.textViewReservationDetails)
        editTextFirstName = view.findViewById(R.id.editTextFirstName)
        editTextLastName = view.findViewById(R.id.editTextLastName)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPhone = view.findViewById(R.id.editTextPhone)
        spinnerPeople = view.findViewById(R.id.spinnerPeople) // หา Spinner จาก Layout
        editTextAdditionalRequest = view.findViewById(R.id.editTextAdditionalRequest)
        buttonConfirmReservation = view.findViewById(R.id.buttonConfirmReservation)

        // เชื่อมโยงตัวแปร TextInputLayout กับ View
        textInputLayoutFirstName = view.findViewById(R.id.textInputLayoutFirstName) // เพิ่มการเชื่อมโยง
        textInputLayoutLastName = view.findViewById(R.id.textInputLayoutLastName) // เพิ่มการเชื่อมโยง
        textInputLayoutEmail = view.findViewById(R.id.textInputLayoutEmail)
        textInputLayoutPhone = view.findViewById(R.id.textInputLayoutPhone)
        textInputLayoutAdditionalRequest = view.findViewById(R.id.textInputLayoutAdditionalRequest)

        // รับข้อมูลที่ส่งมาจาก ReservationDateTimeFragment
        val selectedDate = arguments?.getString("selectedDate")
        val selectedTime = arguments?.getString("selectedTime")
        val selectedCourse = arguments?.getString("selectedCourse")

        // แสดงรายละเอียดการจอง
        val reservationDetailsText = "รายละเอียดการจอง: $selectedDate\n เวลา $selectedTime $selectedCourse"
        textViewReservationDetails.text = reservationDetailsText

        // ตั้งค่า Spinner
        val numberOfPeopleOptions = (1..8).toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, numberOfPeopleOptions)
        spinnerPeople.adapter = adapter

        // ตั้งค่าเริ่มต้น
        spinnerPeople.setSelection(1)

        // เพิ่ม TextWatcher ให้กับ EditText ชื่อ
        editTextFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val firstName = s.toString().trim()
                if (firstName.isEmpty()) {
                    textInputLayoutFirstName.error = "กรุณากรอกชื่อ"
                } else if (!namePattern.matcher(firstName).matches()) {
                    textInputLayoutFirstName.error = "กรุณากรอกชื่อเป็นภาษาไทยหรืออังกฤษเท่านั้น"
                } else {
                    textInputLayoutFirstName.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // เพิ่ม TextWatcher ให้กับ EditText นามสกุล
        editTextLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val lastName = s.toString().trim()
                if (lastName.isEmpty()) {
                    textInputLayoutLastName.error = "กรุณากรอกนามสกุล"
                } else if (!namePattern.matcher(lastName).matches()) {
                    textInputLayoutLastName.error = "กรุณากรอกนามสกุลเป็นภาษาไทยหรืออังกฤษเท่านั้น"
                } else {
                    textInputLayoutLastName.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // เพิ่ม TextWatcher ให้กับ EditText อีเมล
        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()
                if (email.isEmpty()) {
                    textInputLayoutEmail.error = "กรุณากรอกอีเมล"
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    textInputLayoutEmail.error = "รูปแบบอีเมลไม่ถูกต้อง"
                } else {
                    textInputLayoutEmail.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // เพิ่ม TextWatcher ให้กับ EditText เบอร์โทรศัพท์
        editTextPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phone = s.toString().trim()
                if (phone.isEmpty()) {
                    textInputLayoutPhone.error = "กรุณากรอกเบอร์โทรศัพท์"
                } else if (!phonePattern.matcher(phone).matches()) {
                    textInputLayoutPhone.error = "รูปแบบเบอร์โทรศัพท์ไม่ถูกต้อง"
                } else {
                    textInputLayoutPhone.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        buttonConfirmReservation.setOnClickListener {
            val firstName = editTextFirstName.text.toString().trim()
            val lastName = editTextLastName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()
            val numberOfPeople = spinnerPeople.selectedItem.toString().toInt() // ดึงค่าจาก Spinner
            val additionalRequest = editTextAdditionalRequest.text.toString().trim()

            // ตรวจสอบอีกครั้งก่อนไปยังหน้าถัดไป (เผื่อกรณีที่ผู้ใช้ไม่ได้แก้ไขข้อผิดพลาด)
            if (textInputLayoutFirstName.error == null && firstName.isNotEmpty() &&
                textInputLayoutLastName.error == null && lastName.isNotEmpty() &&
                textInputLayoutEmail.error == null && email.isNotEmpty() &&
                textInputLayoutPhone.error == null && phone.isNotEmpty()) {

                // ถ้าผ่านการตรวจสอบทั้งหมด ให้สร้าง Bundle และนำทางไปยัง PaymentFragment
                val bundle = Bundle().apply {
                    putString("selectedDate", selectedDate)
                    putString("selectedTime", selectedTime)
                    putString("selectedCourse", selectedCourse)
                    putString("firstName", firstName)
                    putString("lastName", lastName)
                    putString("email", email)
                    putString("phone", phone)
                    putInt("numberOfPeople", numberOfPeople)
                    putString("additionalRequest", additionalRequest)
                }

                //นำทางไปยัง PaymentFragment
                val paymentFragment = PaymentFragment()
                paymentFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, paymentFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                // ถ้ายังมีข้อผิดพลาด ให้แสดงข้อความแจ้งเตือน (Optional)
                // คุณอาจจะแสดง Toast หรือ Snackbar เพื่อบอกผู้ใช้ว่ายังมีข้อมูลไม่ถูกต้อง
            }
        }
    }
}