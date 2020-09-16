package com.example.escapetrapp.views

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.services.constants.SpendingConstants
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.Spending
import com.example.escapetrapp.views.adapter.SpendingAdapter
import com.example.escapetrapp.views.listener.SpendingListener
import com.example.escapetrapp.viewsmodels.SpendingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class SpendingListFragment : BaseAuthFragment() {

    override val layout = R.layout.fragment_spending_list
    private lateinit var mViewModel: SpendingViewModel
    private lateinit var mAdapter: SpendingAdapter
    private lateinit var mListener: SpendingListener
    private lateinit var btHome: BottomNavigationItemView
    private lateinit var ibBackSpendingList: ImageButton
    private lateinit var tvValueTotal: TextView
    private lateinit var btAddSpendingList: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)
        setUpView(view)

        //Obtendo a recycler
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_all_spending)
        //Definindo um layout, como recycler se comporta na tela
        recycler.layoutManager = LinearLayoutManager(context)
        //Definindo um adapter

        mViewModel.loadAll()

        mListener = object : SpendingListener {

            override fun onClick(id: Int) {
                val bundle = Bundle()
                bundle.putInt(SpendingConstants.SPENDINGID, id)
                findNavController().navigate(R.id.action_spendingListFragment_to_spendingFragment, bundle)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.loadAll()
                total()
            }

            override fun onUpdate(id: Int) {
                val bundle = Bundle()
                bundle.putInt(SpendingConstants.SPENDINGID, id)
                findNavController().navigate(R.id.action_spendingListFragment_to_spendingFragment, bundle)
            }
        }

        mAdapter = SpendingAdapter(mListener)
        recycler.adapter = mAdapter
        registerObserver()
    }

    private fun registerObserver() {
        mViewModel.spendingState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage(it.data)
                    mViewModel.loadAll()
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message)
                }
                is RequestState.Loading -> showLoading("Aguarde um momento") }
        })

        mViewModel.spendingList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            mAdapter.updateSpendings(it)
        })
    }

    private fun setUpView(view: View) {
        val context = view.getContext()
        btHome = view.findViewById(R.id.navigation_home)
        ibBackSpendingList = view.findViewById(R.id.ibBackSpendingList)
        tvValueTotal = view.findViewById(R.id.tvValueTotal)
        btAddSpendingList = view.findViewById(R.id.btAddSpendingList)
        total()
        setUpListener(context)
    }

    private fun setUpListener(context: Context) {
        ibBackSpendingList.setOnClickListener {
            findNavController().navigate(R.id.main_nav_graph)
        }
        btAddSpendingList.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_spendingListFragment_to_spendingFragment)
        }
        btHome.setOnClickListener {
            findNavController().navigate(R.id.main_nav_graph)
        }

    }

    private fun total(){
        tvValueTotal.text = mViewModel.getTotalSpending().toString()
    }

}