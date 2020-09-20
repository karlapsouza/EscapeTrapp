package com.example.escapetrapp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.services.models.Spending
import com.example.escapetrapp.services.models.Trip
import com.example.escapetrapp.views.listener.SpendingListener
import com.example.escapetrapp.views.viewholder.SpendingViewHolder

class SpendingAdapter(private val mListener: SpendingListener) : RecyclerView.Adapter<SpendingViewHolder>(){
    private var mSpendingList: List<Spending> = arrayListOf()
    private var mTripList: List<Trip> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_spending,parent,false)
        return SpendingViewHolder(item, mListener)
    }

    override fun getItemCount(): Int {
        return mSpendingList.count()
    }

    override fun onBindViewHolder(holder: SpendingViewHolder, position: Int) {
        holder.bind(mSpendingList[position])
    }

    fun updateSpendings(list: List<Spending>){
        mSpendingList = list
        notifyDataSetChanged()
    }


}