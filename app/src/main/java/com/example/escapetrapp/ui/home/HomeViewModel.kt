package com.example.escapetrapp.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.escapetrapp.extensions.fromRemoteConfig
import com.example.escapetrapp.models.RequestState
import com.example.escapetrapp.models.dashboardmenu.DashboardItem
import com.example.escapetrapp.models.dashboardmenu.DashboardMenu
import com.example.escapetrapp.utils.featuretoggle.FeatureToggleHelper
import com.example.escapetrapp.utils.featuretoggle.FeatureToggleListener
import com.example.escapetrapp.utils.firebase.RemoteConfigKeys
import com.google.gson.Gson

class HomeViewModel : ViewModel() {
    val menuState = MutableLiveData<RequestState<List<DashboardItem>>>()

    fun createMenu() {
        menuState.value = RequestState.Loading
        val dashboardMenu = Gson().fromRemoteConfig(
            RemoteConfigKeys.MENU_DASHBOARD,
            DashboardMenu::class.java)
        val dashBoardItems = arrayListOf<DashboardItem>()
        val itemsMenu = dashboardMenu?.items ?: listOf()
        for (itemMenu in itemsMenu) {
            FeatureToggleHelper().configureFeature(
            itemMenu.feature, object : FeatureToggleListener {
                override fun onEnabled() {
                    dashBoardItems.add(itemMenu)
                }
                override fun onInvisible() {
                }
                override fun onDisabled(clickListener: (Context) -> Unit) {
                    itemMenu.onDisabledListener = clickListener
                    dashBoardItems.add(itemMenu)
                }
            })
        }
        menuState.value = RequestState.Success(dashBoardItems) }
}