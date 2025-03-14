package com.example.omakase

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omakase.database.OmakaseDatabase
import com.example.omakase.databinding.ActivityMainBinding
import com.example.omakase.repository.CourseRepository
import com.example.omakase.viewmodel.CourseViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val courseViewModel: CourseViewModel by viewModels {
        CourseViewModel.CourseViewModelFactory(
            CourseRepository(OmakaseDatabase.getDatabase(application).courseDao())
        )
    }
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ตั้งค่า RecyclerView
        binding.recyclerViewCourses.layoutManager = LinearLayoutManager(this)
        courseAdapter = CourseAdapter()
        binding.recyclerViewCourses.adapter = courseAdapter

        // สังเกต StateFlow ของ allCourses จาก ViewModel
        lifecycleScope.launch {
            courseViewModel.allCourses.collect { courses ->
                // อัปเดต Adapter ด้วย List ของ Courses ที่ได้รับ
                courseAdapter.submitList(courses)
            }
        }
    }
}