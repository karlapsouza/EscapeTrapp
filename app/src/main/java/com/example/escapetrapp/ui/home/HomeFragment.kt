package com.example.escapetrapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.models.RequestState
import com.example.escapetrapp.models.dashboardmenu.DashboardItem

class HomeFragment : BaseFragment() {
    override val layout = R.layout.fragment_home
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var rvHomeDashboard: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        homeViewModel.createMenu()
        registerObserver()
    }

    private fun setUpView(view: View) {
        rvHomeDashboard = view.findViewById(R.id.rvHomeDashboard)
    }

    private fun registerObserver() {
        homeViewModel.menuState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()
                    setUpMenu(it.data)
                }
                is RequestState.Error -> {
                    hideLoading()
                }
            }
        })
    }

    private fun setUpMenu(items: List<DashboardItem>) {
        rvHomeDashboard.adapter = HomeListAdapter(items, this::clickItem)
    }

    private fun clickItem(item: DashboardItem) {
        item.onDisabledListener.let {
            it?.invoke(requireContext())
        }
        if (item.onDisabledListener == null) {
            showMessage(item.label)
        }
    }
}