package com.example.escapetrapp.views.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.services.models.Spot
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.views.listener.SpotListener
import com.example.escapetrapp.views.listener.TripListener

class SpotViewHolder(itemView: View, private val listener: SpotListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(spot: Spot){
        val btEdit = itemView.findViewById<ImageButton>(R.id.btEditSpot)
        val btDelete = itemView.findViewById<ImageButton>(R.id.btDeleteSpot)

        val spotName = itemView.findViewById<TextView>(R.id.tvSpotName)
        spotName.text = spot.place

        val spotStartTime = itemView.findViewById<TextView>(R.id.tvSpotStartTime)
        spotStartTime.text = spot.startTime

        val sportEndTime = itemView.findViewById<TextView>(R.id.tvSpotEndTime)
        sportEndTime.text = spot.endTime

        btEdit.setOnClickListener {
            listener.onClick(spot.id)
        }

        btDelete.setOnClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.title_delete_spot)
                .setMessage(R.string.message_delete_spot)
                .setPositiveButton(R.string.yes){ dialog, which ->
                    listener.onDelete(spot.id)
                }
                .setNeutralButton(R.string.button_cancel, null)
                .show()
        }
    }
}