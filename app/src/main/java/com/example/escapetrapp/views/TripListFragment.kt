package com.example.escapetrapp.views

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.services.constants.TripConstants
import com.example.escapetrapp.views.adapter.TripAdapter
import com.example.escapetrapp.viewsmodels.TripViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.escapetrapp.views.listener.TripListener as TripListener

class TripListFragment : BaseAuthFragment(){
    override val layout = R.layout.fragment_trip_list

    private lateinit var btAddTrip: FloatingActionButton
    private lateinit var ibBackTripList: ImageButton
    private lateinit var mViewModel: TripViewModel
    private val mAdapter: TripAdapter = TripAdapter()
    private lateinit var mListener: TripListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)

        mViewModel = ViewModelProvider(this).get(TripViewModel::class.java)
        //Obtendo a recycler
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_all_trips)
        //Definindo um layout, como recycler se comporta na tela
        recycler.layoutManager = LinearLayoutManager(context)
        //Definindo um adapter
        recycler.adapter = mAdapter

        mListener = object:TripListener {

            override fun onClick(id: Int){
                val bundle = Bundle()
                bundle.putInt(TripConstants.TRIPID, id)
                findNavController().navigate(R.id.action_tripListFragment_to_spotListFragment, bundle)
            }

            override fun onUpdate(id: Int) {
                val bundle = Bundle()
                bundle.putInt(TripConstants.TRIPID, id)
                findNavController().navigate(R.id.action_tripListFragment_to_tripFragment, bundle)
            }

            override fun onDelete(id: Int){
                mViewModel.delete(id)
                mViewModel.loadAll()
            }

        }
        mAdapter.attachListener(mListener)
        observer()
        mViewModel.loadAll()

    }

    private fun setUpView(view: View) {
        btAddTrip = view.findViewById(R.id.btAddTrip)
        ibBackTripList = view.findViewById(R.id.ibBackTripList)
        setUpListener()
    }

    private fun setUpListener() {
        btAddTrip.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_tripListFragment_to_tripFragment)
        }
        ibBackTripList.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_tripListFragment_to_homeFragment)
        }
    }

    private fun observer(){
        mViewModel.tripList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateTrips(it)
        })
    }


}