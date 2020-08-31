package com.example.escapetrapp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.views.listener.TripListener
import com.example.escapetrapp.views.viewholder.TripViewHolder

class TripAdapter : RecyclerView.Adapter<TripViewHolder>(){
    private var mTripList: List<Trip> = arrayListOf()
    private lateinit var mListener : TripListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_trip,parent,false)
        return TripViewHolder(item, mListener)
    }

    override fun getItemCount(): Int {
        return mTripList.count()
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(mTripList[position])
    }

    fun updateTrips(list: List<Trip>){
        mTripList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: TripListener){
        mListener = listener
    }

}