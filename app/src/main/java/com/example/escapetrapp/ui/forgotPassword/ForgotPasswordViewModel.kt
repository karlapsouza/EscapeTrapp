package com.example.escapetrapp.ui.forgotPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.escapetrapp.exceptions.EmailInvalidException
import com.example.escapetrapp.models.RequestState
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel: ViewModel(){

    val resetPasswordState = MutableLiveData<RequestState<String>>()

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
                }
            }

        } else {
            resetPasswordState.value = RequestState.Error(EmailInvalidException())
        }
    }
}