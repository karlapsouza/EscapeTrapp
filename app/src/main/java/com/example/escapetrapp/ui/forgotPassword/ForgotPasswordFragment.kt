package com.example.escapetrapp.ui.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.exceptions.EmailInvalidException
import com.example.escapetrapp.models.RequestState
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : BaseFragment() {

    override val layout = R.layout.fragment_forgot_password

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}