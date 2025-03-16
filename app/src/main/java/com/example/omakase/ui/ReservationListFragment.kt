package com.example.omakase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.omakase.R
import com.example.omakase.database.AppDatabase
import com.example.omakase.ReservationAdapter
import com.example.omakase.viewmodel.ReservationListViewModel
import com.example.omakase.viewmodel.ReservationListViewModelFactory
import kotlinx.coroutines.launch

class ReservationListFragment : Fragment() {

    private lateinit var recyclerViewReservations: RecyclerView
    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var textViewEmptyReservations: TextView
    private lateinit var viewModel: ReservationListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_reservation_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewReservations = view.findViewById(R.id.recyclerViewReservations)
        textViewEmptyReservations = view.findViewById(R.id.textViewEmptyReservations)
        recyclerViewReservations.layoutManager = LinearLayoutManager(requireContext())

        // สร้าง Database และ ViewModelFactory
        val db = AppDatabase.getDatabase(requireContext(), lifecycleScope)
        val reservationDao = db.reservationDao()
        val factory = ReservationListViewModelFactory(reservationDao)

        // สร้าง ViewModel
        viewModel = ViewModelProvider(this, factory)[ReservationListViewModel::class.java]

        // กำหนด Adapter โดยไม่ต้องใช้ emptyList()
        reservationAdapter = ReservationAdapter()
        recyclerViewReservations.adapter = reservationAdapter

        // ใช้ lifecycleScope.launch (ถ้า ViewModel ใช้ suspend function)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reservations.observe(viewLifecycleOwner) { reservations ->
                if (reservations.isNotEmpty()) {
                    reservationAdapter.submitList(reservations)
                    textViewEmptyReservations.visibility = View.GONE
                    recyclerViewReservations.visibility = View.VISIBLE
                } else {
                    textViewEmptyReservations.visibility = View.VISIBLE
                    recyclerViewReservations.visibility = View.GONE
                }
            }
        }
    }
}
