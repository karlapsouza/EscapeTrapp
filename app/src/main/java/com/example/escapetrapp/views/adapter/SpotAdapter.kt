package com.example.escapetrapp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.services.models.Spot
import com.example.escapetrapp.views.listener.SpotListener
import com.example.escapetrapp.views.viewholder.SpotViewHolder

class SpotAdapter : RecyclerView.Adapter<SpotViewHolder>(){
    private var mSpotList: List<Spot> = arrayListOf()
    private lateinit var mListener : SpotListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_spot,parent,false)
        return SpotViewHolder(item, mListener)
    }

    override fun getItemCount(): Int {
        return mSpotList.count()
    }

    override fun onBindViewHolder(holder: SpotViewHolder, position: Int) {
        holder.bind(mSpotList[position])
    }

    fun updateTrips(list: List<Spot>){
        mSpotList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: SpotListener){
        mListener = listener
    }
}