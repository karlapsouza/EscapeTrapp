package com.example.escapetrapp.ui.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.exceptions.EmailInvalidException
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.models.RequestState
import com.example.escapetrapp.ui.singUp.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : BaseFragment() {

    override val layout = R.layout.fragment_forgot_password
    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModels()

    private lateinit var etEmailRemember: EditText
    private lateinit var btSendEmail: Button
    private lateinit var tvBack: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerObserver()
    }

    private fun setUpView(view: View){
        etEmailRemember = view.findViewById(R.id.etEmailRemember)
        btSendEmail = view.findViewById(R.id.btSendEmail)
        tvBack = view.findViewById(R.id.tvBack)
        setUpListener()
    }

    private fun registerObserver() {
        this.forgotPasswordViewModel.resetPasswordState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage("E-mail enviado!")
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.trowable.message)
                }
                is RequestState.Loading -> showLoading("Aguarde um momento") }
        })
    }

    private fun setUpListener(){
        tvBack.setOnClickListener{
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }
        btSendEmail.setOnClickListener{
            hideKeyboard()
            forgotPasswordViewModel.resetPassword(etEmailRemember.text.toString())
        }
    }


}