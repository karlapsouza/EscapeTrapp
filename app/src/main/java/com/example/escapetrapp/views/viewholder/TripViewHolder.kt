package com.example.escapetrapp.views.viewholder


import android.app.AlertDialog
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.views.listener.TripListener

//Guarda as referências dos elementos de layout

class TripViewHolder(itemView: View, private val listener: TripListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(trip: Trip){
        val btEdit = itemView.findViewById<ImageButton>(R.id.btEdit)
        val btDelete = itemView.findViewById<ImageButton>(R.id.btDelete)
        val btSpot = itemView.findViewById<ImageButton>(R.id.btTouristSpots)
        val btSpending = itemView.findViewById<ImageButton>(R.id.btSpending)

        val tripName = itemView.findViewById<TextView>(R.id.tvNameTrip)
        tripName.text = trip.name

        val tripDestination = itemView.findViewById<TextView>(R.id.tvDestinationTrip)
        tripDestination.text = trip.destination

        val tripInitialDate = itemView.findViewById<TextView>(R.id.tvStartDateTrip)
        tripInitialDate.text = trip.initialDate

        val tripEndDate = itemView.findViewById<TextView>(R.id.tvEndDateTrip)
        tripEndDate.text = trip.endDate

        btEdit.setOnClickListener {
            listener.onUpdate(trip.id)
        }

        btDelete.setOnClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.title_delete_trip)
                .setMessage(R.string.message_delete_trip)
                .setPositiveButton(R.string.yes){ dialog, which ->
                    listener.onDelete(trip.id)
                }
                .setNeutralButton(R.string.button_cancel, null)
                .show()
        }

        btSpot.setOnClickListener {
            listener.onClick(trip.id)
        }

        btSpending.setOnClickListener {
            listener.onView(trip.id)
        }
    }


}