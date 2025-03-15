package com.example.omakase.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.omakase.R
import com.example.omakase.database.AppDatabase
import com.example.omakase.viewmodel.CourseSelectionViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CourseSelectionFragment : Fragment(R.layout.fragment_course_selection) {

    private val viewModel: CourseSelectionViewModel by viewModels()
    private lateinit var database: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = AppDatabase.getDatabase(requireContext(), lifecycleScope)
        val menuItemDao = database.menuItemDao()

        val cardViewRegularCourse = view.findViewById<androidx.cardview.widget.CardView>(R.id.cardViewRegularCourse)
        val cardViewPremiumCourse = view.findViewById<androidx.cardview.widget.CardView>(R.id.cardViewPremiumCourse)

        cardViewRegularCourse.setOnClickListener {
            lifecycleScope.launch {
                menuItemDao.getMenuItemsByCourseType("regular").collectLatest { menuItems ->
                    val menuList = menuItems.map { "${it.name}:${it.details ?: ""}" }
                    val regularCourseDetailsDialog = CourseDetailsDialogFragment.newInstance("คอร์สธรรมดา", menuList)
                    regularCourseDetailsDialog.show(childFragmentManager, "RegularCourseDetails")
                    viewModel.selectCourse("คอร์สธรรมดา")
                }
            }
        }

        cardViewPremiumCourse.setOnClickListener {
            lifecycleScope.launch {
                menuItemDao.getMenuItemsByCourseType("premium").collectLatest { menuItems ->
                    val menuList = menuItems.map { "${it.name}:${it.details ?: ""}" }
                    val premiumCourseDetailsDialog = CourseDetailsDialogFragment.newInstance("คอร์สพรีเมี่ยม", menuList)
                    premiumCourseDetailsDialog.show(childFragmentManager, "PremiumCourseDetails")
                    viewModel.selectCourse("คอร์สพรีเมี่ยม")
                }
            }
        }
    }
}