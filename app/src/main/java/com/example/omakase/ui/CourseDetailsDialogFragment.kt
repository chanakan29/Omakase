package com.example.omakase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.omakase.R

class CourseDetailsDialogFragment : DialogFragment() {

    private var courseName: String? = null
    private var courseDetails: List<String>? = null

    private lateinit var textViewCourseName: TextView
    private lateinit var linearLayoutMenu: LinearLayout
    private lateinit var buttonNextToReservationTime: Button

    companion object {
        fun newInstance(courseName: String, courseDetails: List<String>): CourseDetailsDialogFragment {
            val fragment = CourseDetailsDialogFragment()
            val args = Bundle()
            args.putString("courseName", courseName)
            args.putStringArrayList("courseDetails", ArrayList(courseDetails))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),  // 90% ของจอ
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        courseName = arguments?.getString("courseName")
        courseDetails = arguments?.getStringArrayList("courseDetails")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_course_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewCourseName = view.findViewById(R.id.textViewCourseName)
        linearLayoutMenu = view.findViewById(R.id.linearLayoutMenu)
        buttonNextToReservationTime = view.findViewById(R.id.buttonNextToReservationTime)

        textViewCourseName.text = courseName

        courseDetails?.forEach { detail ->
            val textView = TextView(requireContext())
            textView.text = detail
            linearLayoutMenu.addView(textView)

            textView.setPadding(16, 8, 16, 8)

            if (detail.contains(":")) {
                val parts = detail.split(":", limit = 2)
                val menuTitle = parts[0].trim()
                val menuDescription = parts[1].trim()

                // สร้าง TextView สำหรับชื่อเมนู
                val titleTextView = TextView(requireContext())
                titleTextView.text = menuTitle
                titleTextView.textSize = 16f
                titleTextView.setTypeface(null, android.graphics.Typeface.BOLD)
                titleTextView.setPadding(16, 16, 16, 4)
                linearLayoutMenu.addView(titleTextView)

                // สร้าง TextView สำหรับรายละเอียด
                val descriptionTextView = TextView(requireContext())
                descriptionTextView.text = menuDescription
                descriptionTextView.textSize = 14f
                descriptionTextView.setPadding(32, 4, 16, 16)
                linearLayoutMenu.addView(descriptionTextView)
            } else {
                // ถ้าเป็นรายการทั่วไป
                textView.textSize = 14f
                linearLayoutMenu.addView(textView)
            }

            // เพิ่มเส้นคั่นระหว่างรายการ
            val divider = View(requireContext())
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
            params.setMargins(16, 8, 16, 8)
            divider.layoutParams = params
            divider.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
            linearLayoutMenu.addView(divider)
        }

        buttonNextToReservationTime.setOnClickListener {
            navigateToReservation(courseName ?: "ไม่ระบุ")
            dismiss()
        }
    }

    private fun navigateToReservation(courseType: String) {
        val fragment = ReservationDateTimeFragment()
        val bundle = Bundle()
        bundle.putString("courseType", courseType)
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction() // เปลี่ยนตรงนี้
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }
}