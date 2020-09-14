package com.example.escapetrapp.views

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.services.constants.SpendingConstants
import com.example.escapetrapp.views.adapter.SpendingAdapter
import com.example.escapetrapp.views.listener.SpendingListener
import com.example.escapetrapp.viewsmodels.SpendingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SpendingListFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_spending_list
    private lateinit var mViewModel: SpendingViewModel
    private val mAdapter: SpendingAdapter = SpendingAdapter()
    private lateinit var mListener: SpendingListener

    private lateinit var btHome: BottomNavigationItemView
    private lateinit var ibBackSpendingList: ImageButton
    private lateinit var btAddSpendingList: FloatingActionButton


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)

        mViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)
        //Obtendo a recycler
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_all_spending)
        //Definindo um layout, como recycler se comporta na tela
        recycler.layoutManager = LinearLayoutManager(context)
        //Definindo um adapter
        recycler.adapter = mAdapter

        mListener = object : SpendingListener {

            override fun onClick(id: Int){
                val bundle = Bundle()
                bundle.putInt(SpendingConstants.SPENDINGID, id)
                findNavController().navigate(R.id.action_spendingListFragment_to_spendingFragment, bundle)
            }

            override fun onUpdate(id: Int) {
                val bundle = Bundle()
                bundle.putInt(SpendingConstants.SPENDINGID, id)
                findNavController().navigate(R.id.action_spendingListFragment_to_spendingFragment, bundle)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.loadAll()
            }

        }
        mAdapter.attachListener(mListener)
        observer()
        mViewModel.loadAll()
    }

    private fun setUpView(view: View) {
        btHome = view.findViewById(R.id.navigation_home)
        ibBackSpendingList = view.findViewById(R.id.ibBackSpendingList)
        btAddSpendingList = view.findViewById(R.id.btAddSpendingList)
        setUpListener()
    }

    private fun setUpListener() {
        btHome.setOnClickListener {
            findNavController().navigate(R.id.main_nav_graph)
        }
        ibBackSpendingList.setOnClickListener {
            findNavController().navigate(R.id.action_spendingListFragment_to_homeFragment)
        }
        btAddSpendingList.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_spendingListFragment_to_spendingFragment)
        }

    }

    private fun observer(){
        mViewModel.spendingList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateSpendings(it)
        })
    }

}