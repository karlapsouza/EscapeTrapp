package com.example.escapetrapp.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.services.constants.DataBaseConstants
import com.example.escapetrapp.services.constants.TripConstants
import com.example.escapetrapp.views.adapter.TripAdapter
import com.example.escapetrapp.views.listener.TripListener
import com.example.escapetrapp.viewsmodels.TripListViewModel

class TripListFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_travel_list

    private lateinit var btAddTrip: Button
    private lateinit var allTripViewModel: TripListViewModel
    private val mAdapter: TripAdapter = TripAdapter()
    private lateinit var mListener: TripListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)

        allTripViewModel = ViewModelProvider(this).get(TripListViewModel::class.java)
        //Obtendo a recycler
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_all_trips)
        //Definindo um layout, como recycler se comporta na tela
        recycler.layoutManager = LinearLayoutManager(context)
        //Definindo um adapter
        recycler.adapter = mAdapter

        mListener = object : TripListener() {
            override fun onClick(id: Int){
                val intent = Intent(context, TripListFragment::class.java)

                val bundle = Bundle()
                bundle.putInt(TripConstants.TRIPID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }


        mAdapter.attachListener(mListener)
        observer()
        allTripViewModel.load()

    }

    private fun setUpView(view: View) {
        btAddTrip = view.findViewById(R.id.btAddTrip)
        setUpListener()
    }

    private fun setUpListener() {
        btAddTrip.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_travelListFragment_to_travelFragment)
        }


    }

    private fun observer(){
        allTripViewModel.tripList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateTrips(it)
        })
    }
}