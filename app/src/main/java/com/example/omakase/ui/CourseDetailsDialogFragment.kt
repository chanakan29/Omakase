package com.example.omakase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.omakase.R

class CourseDetailsDialogFragment : DialogFragment() {

    private var courseName: String? = null
    private var menuItems: List<String>? = null

    companion object {
        fun newInstance(courseName: String, menuList: List<String>): CourseDetailsDialogFragment {
            val fragment = CourseDetailsDialogFragment()
            val args = Bundle()
            args.putString("courseName", courseName)
            args.putStringArrayList("menuItems", ArrayList(menuList))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        courseName = arguments?.getString("courseName")
        menuItems = arguments?.getStringArrayList("menuItems")?.toList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_course_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewCourseName = view.findViewById<TextView>(R.id.textViewCourseName)
        val linearLayoutMenu = view.findViewById<LinearLayout>(R.id.linearLayoutMenu)
        val buttonNextToReservationTime = view.findViewById<Button>(R.id.buttonNextToReservationTime)

        textViewCourseName.text = courseName

        menuItems?.forEach { menuItem ->
            val textViewMenuItem = TextView(context)
            textViewMenuItem.text = menuItem
            textViewMenuItem.textSize = 16f
            textViewMenuItem.setPadding(0, 0, 0, 16)
            linearLayoutMenu.addView(textViewMenuItem)
        }

        buttonNextToReservationTime.setOnClickListener {
            // TODO: นำทางไปยังหน้าเลือกวันและเวลา พร้อมข้อมูลคอร์สที่เลือก
            dismiss() // ปิด Dialog
        }
    }
}