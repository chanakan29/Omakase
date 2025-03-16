package com.example.omakase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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