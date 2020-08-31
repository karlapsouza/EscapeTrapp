package com.example.escapetrapp.viewsmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.escapetrapp.extensions.fromRemoteConfig
import com.example.escapetrapp.services.models.RequestState
import com.example.escapetrapp.services.models.dashboardmenu.DashboardItem
import com.example.escapetrapp.services.models.dashboardmenu.DashboardMenu
import com.example.escapetrapp.utils.featuretoggle.FeatureToggleHelper
import com.example.escapetrapp.utils.featuretoggle.FeatureToggleListener
import com.example.escapetrapp.utils.firebase.RemoteConfigKeys
import com.example.escapetrapp.utils.firebase.RemoteConfigUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson

class HomeViewModel : ViewModel() {
    val menuState = MutableLiveData<RequestState<List<DashboardItem>>>()
    private val db = FirebaseFirestore.getInstance()
    var dashboardMenu: DashboardMenu? = null
    val userNameState = MutableLiveData<RequestState<String>>()
    val logoutState = MutableLiveData<RequestState<String>>()
    val tripState = MutableLiveData<RequestState<String>>()

    private fun getUser() {
        userNameState.value = RequestState.Loading
        val user = FirebaseAuth.getInstance().uid
        if (user == null) {
            userNameState.value = RequestState.Error(Throwable("Usuário deslogado"))
        } else {
            db.collection("users")
                .document(FirebaseAuth.getInstance().uid ?: "")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    saveToken()
                    val userName = documentSnapshot.data?.get("username") as String
                    userNameState.value = RequestState.Success(userName)
                }
                .addOnFailureListener {
                    userNameState.value = RequestState.Error(it)
                }
        }
    }

    fun createMenu() {
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
        menuState.value = RequestState.Loading
        RemoteConfigUtils.fetchAndActivate().addOnCompleteListener {
            dashboardMenu = Gson().fromRemoteConfig(
                RemoteConfigKeys.MENU_DASHBOARD,
                DashboardMenu::class.java
            )
            getUser()
        }
        menuState.value = RequestState.Success(dashBoardItems)
    }

    fun signOut() {
        logoutState.value = RequestState.Loading
        FirebaseAuth.getInstance().signOut()
        logoutState.value = RequestState.Success("")
    }

    fun listTrip() {
        tripState.value = RequestState.Loading
        tripState.value = RequestState.Success("")
    }

    private fun saveToken() {
        val user = FirebaseAuth.getInstance().uid
        if (user != null)
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
                db.collection("users")
                    .document(FirebaseAuth.getInstance().uid ?: "")
                    .update("token", it.token)
                    .addOnSuccessListener {}
                    .addOnFailureListener {}
            }
    }
}