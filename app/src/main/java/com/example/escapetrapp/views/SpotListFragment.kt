package com.example.escapetrapp.views


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.services.constants.SpendingConstants
import com.example.escapetrapp.services.constants.SpotConstants
import com.example.escapetrapp.services.constants.TripConstants
import com.example.escapetrapp.views.adapter.SpotAdapter
import com.example.escapetrapp.views.listener.SpotListener
import com.example.escapetrapp.viewsmodels.SpotListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SpotListFragment : BaseFragment() {
    override val layout = R.layout.fragment_spot_list

    private lateinit var btAddSpot: FloatingActionButton
    private lateinit var ibBackSpotList: ImageButton
    private lateinit var mViewModel: SpotListViewModel
    private val mAdapter: SpotAdapter = SpotAdapter()
    private lateinit var mListener: SpotListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerBackPressedAction()

        mViewModel = ViewModelProvider(this).get(SpotListViewModel::class.java)
        //Obtendo a recycler
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_all_spots)
        //Definindo um layout, como recycler se comporta na tela
        recycler.layoutManager = LinearLayoutManager(context)
        //Definindo um adapter
        recycler.adapter = mAdapter

        mListener = object: SpotListener {
            override fun onClick(idSpot: Int, idTrip: Int){
                val bundle = Bundle()
                bundle.putInt(SpotConstants.SPOTID, idSpot)
                bundle.putInt(TripConstants.TRIPID, idTrip)
                findNavController().navigate(R.id.action_spotListFragment_to_spotFragment, bundle)
            }
            override fun onDelete(id: Int){
                mViewModel.delete(id)
                mViewModel.load()
            }

        }
        mAdapter.attachListener(mListener)
        mViewModel.load()

    }

    private fun setUpView(view: View) {
        btAddSpot = view.findViewById(R.id.btAddSpot)
        ibBackSpotList = view.findViewById(R.id.ibBackSpotList)
        setUpListener()
    }

    private fun setUpListener() {
        btAddSpot.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_spotListFragment_to_spotFragment)
        }
        ibBackSpotList.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_spotListFragment_to_tripListFragment)
        }
    }


    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_spotListFragment_to_tripListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}