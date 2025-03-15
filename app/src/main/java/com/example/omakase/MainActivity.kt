package com.example.omakase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.omakase.databinding.ActivityMainBinding
import com.example.omakase.ui.CourseSelectionFragment // Import CourseSelectionFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ตั้งค่า OnClickListener ให้กับปุ่ม "เลือกคอร์สและจอง"
        binding.buttonMakeReservation.setOnClickListener {
            val courseSelectionFragment = CourseSelectionFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, courseSelectionFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}