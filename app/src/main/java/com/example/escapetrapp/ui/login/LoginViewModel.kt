package com.example.escapetrapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.escapetrapp.extensions.isValidEmail
import com.example.escapetrapp.models.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel: ViewModel() {

    private var mAuth = FirebaseAuth.getInstance()

    val loginState = MutableLiveData<RequestState<FirebaseUser>>()

    fun singIn(email: String, password: String){
        loginState.value = RequestState.Loading

        if(validateFields(email,password)){
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                if(it.isSuccessful){
                    loginState.value = RequestState.Success(mAuth.currentUser!!)
                }else{
                    loginState.value = RequestState.Error(Throwable(it.exception?.message ?: "Não foi possível realizar a requisição"))
                }
            }
        }
    }

    private fun validateFields(email: String, password: String): Boolean {
        if(email.isEmpty() || password.isEmpty()){
            loginState.value = RequestState.Error(Throwable("Campos e-mail e senha são obrigatórios"))
            return false
        }

        if(!email.isValidEmail()){
            loginState.value = RequestState.Error(Throwable("E-mail inválido"))
            return false
        }

        if(password.length < 6){
            loginState.value = RequestState.Error(Throwable("Senha deve contém mais de 5 caracteres"))
            return false
        }

        return true
    }

}