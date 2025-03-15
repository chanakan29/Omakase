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

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = (resources.displayMetrics.widthPixels * 0.90).toInt() // กำหนดความกว้างเป็น 90% ของหน้าจอ
            it.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
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

        linearLayoutMenu.removeAllViews()

        menuItems?.forEach { menuItem ->
            val menuItemView = layoutInflater.inflate(R.layout.item_menu_detail, linearLayoutMenu, false)
            val textViewMenuItemName = menuItemView.findViewById<TextView>(R.id.textViewMenuItemName)
            val textViewMenuItemDetail = menuItemView.findViewById<TextView>(R.id.textViewMenuItemDetail)

            val parts = menuItem.split(":") // สมมติว่าชื่อเมนูและรายละเอียดคั่นด้วย ":"
            if (parts.size == 2) {
                textViewMenuItemName.text = parts[0].trim()
                textViewMenuItemDetail.text = parts[1].trim()
            } else {
                textViewMenuItemName.text = menuItem // ถ้าไม่มี ":" ให้แสดงเป็นชื่อเมนูอย่างเดียว
                textViewMenuItemDetail.visibility = View.GONE // ซ่อน TextView รายละเอียด
            }

            linearLayoutMenu.addView(menuItemView)
        }

        buttonNextToReservationTime.setOnClickListener {
            // TODO: นำทางไปยังหน้าเลือกวันและเวลา พร้อมข้อมูลคอร์สที่เลือก
            dismiss() // ปิด Dialog
        }
    }
}