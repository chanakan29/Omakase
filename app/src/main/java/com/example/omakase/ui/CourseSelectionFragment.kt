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
            val regularCourseDetailsDialog = CourseDetailsDialogFragment.newInstance("คอร์สธรรมดา", listOf("เมนู 1: รายละเอียดเมนู 1", "เมนู 2: รายละเอียดเมนู 2", "เมนู 3 (ธรรมดา): รายละเอียดเมนู 3 (ธรรมดา)"))
            regularCourseDetailsDialog.show(childFragmentManager, "RegularCourseDetails")
            viewModel.selectCourse("คอร์สธรรมดา")
        }

        cardViewPremiumCourse.setOnClickListener {
            // สร้างและแสดง Dialog สำหรับคอร์สพรีเมี่ยม
            val premiumCourseDetailsDialog = CourseDetailsDialogFragment.newInstance("คอร์สพรีเมี่ยม", listOf("เมนู 1: รายละเอียดเมนู 1", "เมนู 2: รายละเอียดเมนู 2", "เมนู 3 (พรีเมี่ยม): รายละเอียดเมนู 3 (พรีเมี่ยม)", "เมนู 4: รายละเอียดเมนู 4"))
            premiumCourseDetailsDialog.show(childFragmentManager, "PremiumCourseDetails")
            viewModel.selectCourse("คอร์สพรีเมี่ยม")
        }
    }
}