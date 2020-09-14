package com.example.escapetrapp.views


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.services.constants.SpotConstants
import com.example.escapetrapp.views.adapter.SpotAdapter
import com.example.escapetrapp.views.listener.SpotListener
import com.example.escapetrapp.viewsmodels.SpotListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SpotListFragment : BaseAuthFragment() {
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
            override fun onClick(id: Int){
                val intent = Intent(context, SpotListFragment::class.java)

                //para passar dados na intent
                val bundle = Bundle()
                bundle.putInt(SpotConstants.SPOTID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            override fun onDelete(id: Int){
                mViewModel.delete(id)
                mViewModel.load()
            }

        }

        mAdapter.attachListener(mListener)
        observer()
        mViewModel.load()

    }

    private fun setUpView(view: View) {
        btAddSpot = view.findViewById(R.id.btAddSpot)
        ibBackSpotList = view.findViewById(R.id.ibBackSpotList)
        setUpListener()
    }

    private fun setUpListener() {
        btAddSpot.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_spotListFragment_to_spotsFragment)
        }
        ibBackSpotList.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_spotListFragment_to_travelListFragment)
        }
    }

    private fun observer(){
//        mViewModel.tripList.observe(viewLifecycleOwner, Observer {
//            mAdapter.updateSpots(it)
//        })
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}