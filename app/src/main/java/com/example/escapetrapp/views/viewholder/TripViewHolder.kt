package com.example.escapetrapp.views.viewholder


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.services.models.Trip

//Guarda as referências dos elementos de layout

class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(trip: Trip){
        val tripName = itemView.findViewById<TextView>(R.id.tvNameTrip)
        tripName.text = trip.name

        val tripDestination = itemView.findViewById<TextView>(R.id.tvDestinationTrip)
        tripDestination.text = trip.destination

        val tripInitialDate = itemView.findViewById<TextView>(R.id.tvStartDateTrip)
        tripInitialDate.text = trip.initialDate

        val tripEndDate = itemView.findViewById<TextView>(R.id.tvEndDateTrip)
        tripEndDate.text = trip.endDate
    }
}