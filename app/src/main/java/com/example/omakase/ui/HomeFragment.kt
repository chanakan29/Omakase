package com.example.omakase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.omakase.R
import com.example.omakase.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonMakeReservation.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragmentContainerView, CourseSelectionFragment())
                addToBackStack(null) // เพิ่ม BackStack ถ้าต้องการให้กด Back กลับมาหน้าหลักได้
            }
        }

        // เพิ่ม Listener สำหรับปุ่มดูรายการจอง
        binding.buttonViewReservations?.setOnClickListener { // ใช้ ?. เพื่อป้องกัน NullPointerException
            parentFragmentManager.commit {
                replace(R.id.fragmentContainerView, ReservationListFragment())
                addToBackStack(null) // เพิ่ม BackStack ถ้าต้องการให้กด Back กลับมาหน้าหลักได้
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}