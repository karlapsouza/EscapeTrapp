package com.example.escapetrapp.views.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.services.models.Spending
import com.example.escapetrapp.views.listener.SpendingListener

class SpendingViewHolder (itemView: View, private val listener: SpendingListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(spending: Spending){
        val btEdit = itemView.findViewById<ImageButton>(R.id.btEditSpending)
        val btDelete = itemView.findViewById<ImageButton>(R.id.btDeleteSpending)

        val spendingDescription = itemView.findViewById<TextView>(R.id.tvSpendingDescription)
        spendingDescription.text = spending.description

        val spendingValue = itemView.findViewById<TextView>(R.id.tvSpendingValue)
        spendingValue.text = spending.value.toString()

        val tripInitialDate = itemView.findViewById<TextView>(R.id.tvStartDateTrip)
        tripInitialDate.text = spending.date


        btEdit.setOnClickListener {
            listener.onClick(spending.id)
        }

        btDelete.setOnClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.title_delete_spending)
                .setMessage(R.string.message_delete_spending)
                .setPositiveButton(R.string.yes){ dialog, which ->
                    listener.onDelete(spending.id)
                }
                .setNeutralButton(R.string.button_cancel, null)
                .show()
        }
    }
}