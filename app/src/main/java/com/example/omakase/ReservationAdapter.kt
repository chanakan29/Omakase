package com.example.omakase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omakase.entity.Reservation

class ReservationAdapter :
    ListAdapter<Reservation, ReservationAdapter.ReservationViewHolder>(DIFF_CALLBACK) {

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.textViewReservationDate)
        val timeSlotTextView: TextView = itemView.findViewById(R.id.textViewReservationTime)
        val courseTypeTextView: TextView = itemView.findViewById(R.id.textViewReservationCourse)
        val nameTextView: TextView = itemView.findViewById(R.id.textViewReservationName)
        val peopleTextView: TextView = itemView.findViewById(R.id.textViewReservationPeople)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val currentReservation = getItem(position) // ใช้ getItem() แทน reservationList[position]
        holder.dateTextView.text = currentReservation.date
        holder.timeSlotTextView.text = currentReservation.timeSlot
        holder.courseTypeTextView.text = currentReservation.courseType
        holder.nameTextView.text = holder.itemView.context.getString(
            R.string.full_name,
            currentReservation.firstName,
            currentReservation.lastName
        )
        holder.peopleTextView.text = holder.itemView.context.getString(
            R.string.number_of_people,
            currentReservation.numberOfPeople
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Reservation>() {
            override fun areItemsTheSame(oldItem: Reservation, newItem: Reservation): Boolean {
                return oldItem.id == newItem.id // ใช้ id เพื่อตรวจสอบว่าเป็นรายการเดียวกันไหม
            }

            override fun areContentsTheSame(oldItem: Reservation, newItem: Reservation): Boolean {
                return oldItem == newItem // ตรวจสอบว่าข้อมูลเปลี่ยนไหม
            }
        }
    }
}
