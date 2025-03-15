package com.example.omakase.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.omakase.R
import com.example.omakase.viewmodel.CourseSelectionViewModel

class CourseSelectionFragment : Fragment(R.layout.fragment_course_selection) {

    private val viewModel: CourseSelectionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardViewRegularCourse = view.findViewById<androidx.cardview.widget.CardView>(R.id.cardViewRegularCourse)
        val cardViewPremiumCourse = view.findViewById<androidx.cardview.widget.CardView>(R.id.cardViewPremiumCourse)

        cardViewRegularCourse.setOnClickListener {
            // สร้างและแสดง Dialog สำหรับคอร์สธรรมดา
            val regularCourseDetailsDialog = CourseDetailsDialogFragment.newInstance("คอร์สธรรมดา", listOf("เมนู 1 (ธรรมดา)", "เมนู 2 (ธรรมดา)", "เมนู 3 (ธรรมดา)"))
            regularCourseDetailsDialog.show(childFragmentManager, "RegularCourseDetails")
            viewModel.selectCourse("คอร์สธรรมดา") // เก็บข้อมูลคอร์สที่เลือกไว้ใน ViewModel
        }

        cardViewPremiumCourse.setOnClickListener {
            // สร้างและแสดง Dialog สำหรับคอร์สพรีเมี่ยม
            val premiumCourseDetailsDialog = CourseDetailsDialogFragment.newInstance("คอร์สพรีเมี่ยม", listOf("เมนู 1 (พรีเมี่ยม)", "เมนู 2 (พรีเมี่ยม)", "เมนู 3 (พรีเมี่ยม)", "เมนู 4 (พรีเมี่ยม)"))
            premiumCourseDetailsDialog.show(childFragmentManager, "PremiumCourseDetails")
            viewModel.selectCourse("คอร์สพรีเมี่ยม") // เก็บข้อมูลคอร์สที่เลือกไว้ใน ViewModel
        }
    }
}