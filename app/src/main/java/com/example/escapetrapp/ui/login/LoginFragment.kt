package com.example.escapetrapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.exceptions.EmailInvalidException
import com.example.escapetrapp.exceptions.PasswordInvalidException
import com.example.escapetrapp.models.RequestState

class LoginFragment : BaseFragment() {

    private lateinit var btAcessLogin: Button
    private lateinit var etEmailLogin: EditText
    private lateinit var etPasswordLogin: EditText
    private lateinit var tvResetPassword: TextView
    private lateinit var tvNewAccount: TextView

    private val loginViewModel: LoginViewModel by viewModels()

    override val layout = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerObserver()
    }

    private fun registerObserver() {
        loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
            when(it){
                is RequestState.Success -> showSuccess()
                is RequestState.Error -> showError(it.trowable)
                is RequestState.Loading -> showLoading("Realizando login")
            }
        })
    }

    private fun showError(trowable: Throwable) {
        hideLoading()
        showMessage(trowable.message)
        when(trowable){
            is EmailInvalidException -> {
                etEmailLogin.error = trowable.message
                etEmailLogin.requestFocus()
            }
            is PasswordInvalidException -> {
                etPasswordLogin.error = trowable.message
                etPasswordLogin.requestFocus()
            }
        }
    }

    private fun showSuccess() {
        hideLoading()
        NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_main_nav_graph)
    }

    private fun setUpView(view:View) {
        btAcessLogin = view.findViewById(R.id.btAcessLogin)
        etEmailLogin = view.findViewById(R.id.etEmailLogin)
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin)
        tvResetPassword = view.findViewById(R.id.tvResetPassword)
        tvNewAccount = view.findViewById(R.id.tvNewAccount)

        btAcessLogin.setOnClickListener {
            loginViewModel.singIn(
                etEmailLogin.text.toString(),
                etPasswordLogin.text.toString()
            )
        }

        tvResetPassword.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        tvNewAccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_singUpFragment)
        }
    }

}
