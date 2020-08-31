package com.example.escapetrapp.views

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.example.escapetrapp.R
import com.example.escapetrapp.base.auth.BaseAuthFragment
import com.example.escapetrapp.extensions.startDeeplink
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.dashboardmenu.DashboardItem
import com.example.escapetrapp.utils.EscapeTrappTracker
import com.example.escapetrapp.views.adapter.HomeListAdapter
import com.example.escapetrapp.viewsmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_home
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var rvHomeDashboard: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        homeViewModel.createMenu()
        registerObserver()
        registerBackPressedAction()
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
        homeViewModel.userNameState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    tvHomeHelloUser.text = "Carregando ..."
                }
                is RequestState.Success -> {
                    tvHomeHelloUser.text = String.format(homeViewModel.dashboardMenu?.title ?: "", it.data)
                    tvSubTitleSignUp.text = homeViewModel.dashboardMenu?.subTitle }
                is RequestState.Error -> {
                    tvHomeHelloUser.text = "Olá"
                    tvSubTitleSignUp.text = homeViewModel.dashboardMenu?.subTitle
                }
            }
        })
        homeViewModel.logoutState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading() }
                is RequestState.Success -> {
                    hideLoading()
                    findNavController().navigate(R.id.login_nav_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message) }
            }
        })
        homeViewModel.tripState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Loading -> {
                    showLoading() }
                is RequestState.Success -> {
                    hideLoading()
                    findNavController().navigate(R.id.action_homeFragment_to_travelListFragment)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message) }
            }
        })
    }

    private fun setUpMenu(items: List<DashboardItem>) {
        rvHomeDashboard.adapter =
            HomeListAdapter(
                items,
                this::clickItem
            )
    }

    private fun clickItem(item: DashboardItem) {
        item.onDisabledListener.let {
            it?.invoke(requireContext())
        }

        EscapeTrappTracker.trackEvent(requireActivity(),
            bundleOf("feature" to item.feature)
        )

        if (item.onDisabledListener == null) {
            when (item.feature) {
                "SAIR" -> {
                    homeViewModel.signOut()
                }
                "VIAGEM" -> {
                    homeViewModel.listTrip()
                }else -> {
                    startDeeplink(item.action.deeplink)
               }
            }
        }
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