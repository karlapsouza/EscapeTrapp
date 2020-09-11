package com.example.escapetrapp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.services.models.Spending
import com.example.escapetrapp.views.listener.SpendingListener
import com.example.escapetrapp.views.viewholder.SpendingViewHolder

class SpendingAdapter : RecyclerView.Adapter<SpendingViewHolder>(){
    private var mSpendingList: List<Spending> = arrayListOf()
    private lateinit var mListener : SpendingListener

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

    fun attachListener(listener: SpendingListener){
        mListener = listener
    }
}