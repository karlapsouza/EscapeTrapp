package com.example.escapetrapp.ui.singUp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import br.com.concrete.canarinho.watcher.TelefoneTextWatcher
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao
import com.airbnb.lottie.LottieAnimationView

import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.extensions.hideKeyboard
import com.example.escapetrapp.models.RequestState
import com.example.escapetrapp.models.User

class SingUpFragment : BaseFragment() {
    override val layout = R.layout.fragment_sign_up

    private lateinit var tvTerms: TextView
    private lateinit var etPhoneSignUp: EditText
    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var etUserNameSignUp: EditText
    private lateinit var etEmailSignUp: EditText
    private lateinit var etConfirmEmailSignUp: EditText
    private lateinit var etPasswordSignUp: EditText
    private lateinit var etConfirmPasswordSignUp: EditText
    private lateinit var cbTermsSignUp: LottieAnimationView
    private lateinit var btCreateAccount: Button
    private lateinit var tvLoginSignUp: TextView
    private var checkBoxDone = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        registerObserver()
    }

    private fun setUpView(view: View) {
        etUserNameSignUp = view.findViewById(R.id.etUserNameSignUp)
        etEmailSignUp = view.findViewById(R.id.etEmailSignUp)
        etConfirmEmailSignUp = view.findViewById(R.id.etConfirmEmailSignUp)
        etPhoneSignUp = view.findViewById(R.id.etPhoneSignUp)
        etPasswordSignUp = view.findViewById(R.id.etPasswordSignUp)
        etConfirmPasswordSignUp = view.findViewById(R.id.etConfirmPasswordSignUp)
        cbTermsSignUp = view.findViewById(R.id.cbTermsSignUp)
        tvTerms = view.findViewById(R.id.tvTerms)
        btCreateAccount = view.findViewById(R.id.btCreateAccount)
        tvLoginSignUp = view.findViewById(R.id.tvLoginSignUp)
        setUpListener()
    }

    private fun setUpListener() {
        etPhoneSignUp.addTextChangedListener(TelefoneTextWatcher(object : EventoDeValidacao {
            override fun totalmenteValido(valorAtual: String?) {}
            override fun invalido(valorAtual: String?, mensagem: String?) {}
            override fun parcialmenteValido(valorAtual: String?) {}
        }))
        tvTerms.setOnClickListener {
            NavHostFragment.findNavController(this)
            .navigate(R.id.action_singUpFragment_to_termsFragment) }
        btCreateAccount.setOnClickListener {
            hideKeyboard()
            val newUser = User(
                etUserNameSignUp.text.toString(),
                etEmailSignUp.text.toString(),
                etPhoneSignUp.text.toString(),
                etPasswordSignUp.text.toString()
            )
            signUpViewModel.signUp(
            newUser,
            etConfirmEmailSignUp.text.toString(),
            etConfirmPasswordSignUp.text.toString())
        }
        tvLoginSignUp.setOnClickListener {
            NavHostFragment.findNavController(this) .navigate(R.id.main_nav_graph)
        }
        setUpCheckboxListener()
    }

    private fun setUpCheckboxListener() {
        cbTermsSignUp.setOnClickListener {
            if (checkBoxDone) {
                cbTermsSignUp.speed = -1f
                cbTermsSignUp.playAnimation()
                checkBoxDone = false
            } else {
                cbTermsSignUp.speed = 1f
                cbTermsSignUp.playAnimation()
                checkBoxDone = true
            }
        }
    }

    private fun registerObserver() {
        this.signUpViewModel.signUpState.observe(viewLifecycleOwner, Observer {
        when (it) {
            is RequestState.Success -> {
                hideLoading()
                showMessage("Usuário cadastro realizado com sucesso!")
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_singUpFragment_to_loginFragment)
            }
            is RequestState.Error -> {
                hideLoading()
                showMessage(it.trowable.message)
            }
            is RequestState.Loading -> showLoading("Realizando cadastro de usuário") }
    }) }
}

