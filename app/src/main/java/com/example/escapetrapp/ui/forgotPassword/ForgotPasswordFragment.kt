package com.example.escapetrapp.ui.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.escapetrapp.R
import com.example.escapetrapp.base.BaseFragment
import com.example.escapetrapp.exceptions.EmailInvalidException
import com.example.escapetrapp.models.RequestState
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : BaseFragment() {

    val resetPasswordState = MutableLiveData<RequestState<String>>()

    override val layout = R.layout.fragment_forgot_password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    fun resetPassword(email: String) {
        resetPasswordState.value = RequestState.Loading
        if(email.isNotEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email) .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                resetPasswordState.value = RequestState.Success("Verifique sua caixa de e-mail")
            } else {
                resetPasswordState.value = RequestState.Error(
                    Throwable(
                        task.exception?.message ?: "Não foi possível realizar a requisição"
                    ) )
            } }
    } else {
        resetPasswordState.value = RequestState.Error(EmailInvalidException())
    } }


}