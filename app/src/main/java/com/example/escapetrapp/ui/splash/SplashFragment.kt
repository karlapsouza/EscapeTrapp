package com.example.escapetrapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.utils.firebase.RemoteConfigUtils

class SplashFragment : BaseFragment() {

    private lateinit var ivLogoApp: ImageView
    override val layout = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        startAnimation()
        updateRemoteConfig()
    }

    private fun setUpView(view: View) {
        ivLogoApp = view.findViewById(R.id.ivLogoApp)
    }

    private fun startAnimation() {
        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_splash)
        ivLogoApp.startAnimation(anim)
    }

    private fun updateRemoteConfig() {
        Handler().postDelayed({
            RemoteConfigUtils.fetchAndActivate().addOnCompleteListener {
            nextScreen() }
        }, 2000)
    }

    private fun nextScreen() {
        val extras = FragmentNavigatorExtras( ivLogoApp to "logoApp")
        NavHostFragment.findNavController(this).navigate(R.id.action_splashFragment_to_login_nav_graph, null, null, extras)
    }
}
